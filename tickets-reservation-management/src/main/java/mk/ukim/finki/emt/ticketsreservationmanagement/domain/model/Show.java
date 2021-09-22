package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.NoAvailableTicketsException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHallId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@Entity
@Table(name="show")
@Getter
public class Show extends AbstractEntity<ShowId>{
    private int numberTickets;
    @AttributeOverride(name="quantity", column=@Column(name="number_tickets_available", nullable = false))
    private Quantity numberTicketsAvailable;
    @AttributeOverride(name="quantity", column=@Column(name="number_love_tickets_available", nullable = false))
    private Quantity numberLoveTicketsAvailable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @AttributeOverride(name="id", column=@Column(name="cinema_hall_id", nullable = false))
    private CinemaHallId cinemaHallId;
    private Money price;
    @AttributeOverride(name="id", column=@Column(name="movie_id", nullable = false))
    private MovieId movieId;
    private String movieTitle;
    private boolean isEnded;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    protected Show(){
        super(ShowId.randomId(ShowId.class));
    }

    //For every show there are numberTicketsAvailable number of tickets
    //numberLoveTicketsAvailable is how many tickets from all the numberTicketsAvailable are love tickets
    public Show(int numberTickets,
                Quantity numberTicketsAvailable,
                Quantity numberLoveTicketsAvailable,
                LocalDateTime startDateTime,
                LocalDateTime endDateTime,
                CinemaHallId cinemaHallId,
                Money price,
                MovieId movieId,
                String movieTitle) {
        super(ShowId.randomId(ShowId.class));
        this.numberTickets = numberTickets;
        this.numberTicketsAvailable = numberTicketsAvailable;
        this.numberLoveTicketsAvailable = numberLoveTicketsAvailable;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.cinemaHallId = cinemaHallId;
        this.price = calculatePrice(price);
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.isEnded = false;
        for(int i=0; i<numberLoveTicketsAvailable.accessQuantity(); i++){
            Ticket ticket = new Ticket(true,getPriceForLoveSeat(price), this.getId());
            this.tickets.add(ticket);
        }
        for(int i=0; i<numberTicketsAvailable.accessQuantity() - numberLoveTicketsAvailable.accessQuantity(); i++){
            Ticket ticket = new Ticket(false, price, this.getId());
            this.tickets.add(ticket);
        }
    }


    public void showEnded(){
        this.isEnded = true;
    }

    //The price for a love seat is as same as the price of 2 regular tickets, but with 5% discount
    public Money getPriceForLoveSeat(Money price){
        return (price.multiply(2)).multiply(0.95);
    }

    //When ticket is bought, the number of available tickets for the show is decremented
    public void ticketBought(Ticket ticket){
        Objects.requireNonNull(ticket);
        if(ticket.isLoveTicket()){
            this.numberLoveTicketsAvailable.decrementQuantity();
        }
        this.numberTicketsAvailable.decrementQuantity();
    }

    public void changeNumberTicketsAvailable(Quantity numberTicketsAvailable){
        this.numberTicketsAvailable = numberTicketsAvailable;
    }

    public void changeNumberLoveTicketsAvailable(Quantity numberLoveTicketsAvailable){
        this.numberLoveTicketsAvailable = numberLoveTicketsAvailable;
    }


    public void makeReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

    //For the weekends the price for the tickets are 10% higher
    public Money calculatePrice(Money price){
        if(startDateTime.getDayOfWeek().equals(FRIDAY) ||
                startDateTime.getDayOfWeek().equals(SATURDAY) ||
                startDateTime.getDayOfWeek().equals(SUNDAY)){
                   price = price.multiply(1.1);
        }
        return price;
    }

    //This method checks if the show is ended
    public void refreshShow(){
        if(LocalDateTime.now().isAfter(this.endDateTime)){
            this.showEnded();
        }
    }
    public int getNumberTickets(){
        return this.numberTickets;
    }


    public int getSoldTickets(){
        return this.getNumberTickets() - this.getNumberTicketsAvailable().accessQuantity();
    }




}
