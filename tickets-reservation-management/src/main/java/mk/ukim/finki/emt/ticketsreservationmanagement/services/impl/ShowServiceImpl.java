package mk.ukim.finki.emt.ticketsreservationmanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.sharedkernel.domain.events.movies.MovieShowed;
import mk.ukim.finki.emt.sharedkernel.domain.events.shows.ShowEnded;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.MoreThanOneShowPlayingAtTheSameTimeException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.NoAvailableTicketsException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.ReservationIdNotExistException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.ShowIdDoNotExistException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Show;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ticket;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository.ReservationRepository;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository.ShowRepository;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.ShowService;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ReservationForm;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ShowForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ReservationRepository reservationRepository;
    private DomainEventPublisher publisher;
    private final Validator validator;

    @Override
    @Transactional
    public Optional<Show> createNewShow(ShowForm showForm) {
        Objects.requireNonNull(showForm, "Show form must not be null");
        //This prevents to add show in cinema hall while there is another show already playing that time
        long showPlaysAtSameTime = this.showRepository.findAll()
                .stream()
                .filter(s -> s.getCinemaHallId().equals(showForm.getCinemaHall().getId())
                && s.getEndDateTime().isAfter(showForm.getStartDateTime()))
                .count();
        if(showPlaysAtSameTime > 0){
           throw new MoreThanOneShowPlayingAtTheSameTimeException();
        }
        var constraintViolations = validator.validate(showForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The show form is not valid", constraintViolations);
        }
        var newShow = showRepository.saveAndFlush(toDomainObject(showForm));
        return Optional.of(newShow);
    }

    @Override
    public List<Show> findAll() {
        return showRepository.findAll();
    }

    @Override
    public Optional<Show> findById(ShowId id) {
        return showRepository.findById(id);
    }

    @Override
    //This method calculates the total price of sold tickets of some specific show
    public double totalTicketsPrice(ShowId id) {
        Optional<Show> show = this.showRepository.findById(id);
        Money totalPrice = show.get()
                .getPrice()
                .multiply(show.get().getNumberTickets() - show.get().getNumberTicketsAvailable().accessQuantity());
        return totalPrice.getAmount();
    }

    @Override
    public void refreshShows() {
        this.findAll().stream()
                .forEach(show -> show.refreshShow());
    }

    @Override
    public List<Show> findEndedShows() {
       return this.findAll().stream()
               .filter(show -> show.isEnded())
               .collect(Collectors.toList());
    }

    @Override
    //This method publishes an event that the number of sold tickets in a cinema hall is updated
    public void numberTicketsSoldInCinemaHall() {
        List<String> cinemaHallIds = new ArrayList<>();
        for (Show show: findEndedShows()) {
            if(!cinemaHallIds.contains(show.getCinemaHallId().getId()))
            {
                cinemaHallIds.add(show.getCinemaHallId().getId());
            }
        }
        for(String s : cinemaHallIds){
            int totalNumberSold = 0;
            for(Show show: findEndedShows())
            {
                if(show.getCinemaHallId().getId().equals(s))
                {
                    totalNumberSold += show.getSoldTickets();
                }
            }
            publisher.publish(new ShowEnded(s, totalNumberSold));
        }
    }

    @Override
    //This method publishes an event that the number of movies played is updated
    public void numberTimesMoviePlayed() {
        List<String> movieIds = new ArrayList<>();
       for(Show show : findEndedShows())
       {
           if(!movieIds.contains(show.getMovieId().getId()))
           {
               movieIds.add(show.getMovieId().getId());
           }
       }
       for(String movieId: movieIds){
           int times = 0;
           for(Show show: findEndedShows())
           {
               if(movieId.equals(show.getMovieId().getId())){
                   times += 1;
               }
           }
           publisher.publish(new MovieShowed(times, movieId));
       }
    }

    @Override
    public Reservation makeReservationForShow(ShowId showId, ReservationForm reservationForm) {
        Show show = this.showRepository.findById(showId).orElseThrow(ShowIdDoNotExistException::new);
        Objects.requireNonNull(reservationForm, "Reservation form must not be null");
        var constraintViolations = validator.validate(reservationForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The reservation form is not valid", constraintViolations);
        }
        Reservation reservation = new Reservation(showId,
                reservationForm.getCurrency(),
                reservationForm.getCustomerName(),
                reservationForm.getTelephone(),
                reservationForm.getTicketsNumber(),
                reservationForm.getLoveTicketsNumber());

        //If there are not available tickets it prevents the user to make a reservation
        if(reservation.getNumberTickets() <= show.getNumberTicketsAvailable()
                .accessQuantity()-show.getNumberLoveTicketsAvailable()
                .accessQuantity() &&
                reservation.getNumberLoveTickets() <= show.getNumberLoveTicketsAvailable()
                        .accessQuantity()){
            show.makeReservation(reservation);
            //When a reservation is made the number of available tickets gets decremented, and the
            //tickets are set to be unavailable
            show.changeNumberLoveTicketsAvailable(new Quantity(show.getNumberLoveTicketsAvailable().accessQuantity() - reservation.getNumberLoveTickets()));
            show.changeNumberTicketsAvailable(new Quantity(show.getNumberTicketsAvailable().accessQuantity()- (reservation.getNumberLoveTickets() + reservation.getNumberTickets())));
            int loveTicketsNumber = reservation.getNumberLoveTickets();
            int ticketNumber = reservation.getNumberTickets();

            for(Ticket t : show.getTickets()){
                if(loveTicketsNumber != 0){
                    if(t.isAvailable() && t.isLoveTicket()){
                        t.isBought();
                        reservation.addTicket(t);
                        reservationRepository.saveAndFlush(reservation);
                        loveTicketsNumber--;
                    }
                }
                if(ticketNumber != 0){
                    if(t.isAvailable() && !t.isLoveTicket()){
                        t.isBought();
                        reservation.addTicket(t);
                        reservationRepository.saveAndFlush(reservation);
                        ticketNumber--;
                    }
                }
            }
        }else throw new NoAvailableTicketsException();
        this.showRepository.saveAndFlush(show);
        return reservation;
     }


    @Override
    //Returns the shows that are streaming today
    public List<Show> findTodayShows() {
        return findAll().stream()
                .filter(show -> show.getStartDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
                        show.getStartDateTime().getYear() == LocalDateTime.now().getYear())
                .collect(Collectors.toList());
    }

    @Override
    //Returns the shows that are streaming this week starting from now
    public List<Show> findThisWeekShows() {
        return findAll().stream()
                .filter(show->show.getStartDateTime().isBefore(LocalDateTime.now().plusWeeks(1)))
                .collect(Collectors.toList());
    }

    @Override
    //Returns the shows streaming in a specific day this week starting from now
    public List<Show> filterWeekShowsByDay(String day) {
        return findThisWeekShows()
                .stream()
                .filter(show -> show.getStartDateTime()
                        .getDayOfWeek() == DayOfWeek.valueOf(day))
                .collect(Collectors.toList());
    }

    @Override
    //Returns all of the reservations for a specific show
    public List<Reservation> getReservationsForShow(ShowId showId) {
        Show show = this.showRepository.findById(showId).orElseThrow(ShowIdDoNotExistException::new);
        List<Reservation> reservations = new ArrayList<>(show.getReservations());
        return reservations;
    }



    private Show toDomainObject(ShowForm showForm) {
        var show = new Show(new Quantity(showForm.getCinemaHall().getCapacity()).accessQuantity(),
                new Quantity(showForm.getCinemaHall().getCapacity()),
                showForm.getCinemaHall().getNumberLoveSeats(),
                showForm.getStartDateTime(),
                showForm.getEndDateTime(),
                showForm.getCinemaHall().getId(),
                showForm.getPrice(),
                showForm.getMovie().getId(),
                showForm.getMovie().getName());
        return  show;
    }
}
