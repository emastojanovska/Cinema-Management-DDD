package mk.ukim.finki.emt.cinemamanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.cinemamanagement.domain.exceptions.CinemaIdNotFoundException;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Cinema;
import mk.ukim.finki.emt.cinemamanagement.domain.model.CinemaHall;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.cinemamanagement.domain.repository.CinemaRepository;
import mk.ukim.finki.emt.cinemamanagement.services.CinemaService;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaForm;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaHallForm;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    //One cinema represents a building (meaning there may be cinema Cineplex in SCM and one in GTC
    // and they are considered as two different cinemas).
    //In every cinema there are cinema halls. In every cinema hall, after some time, the number of
    //total shows that were streaming in that specific cinema hall gets updated
    @Override
    public CinemaId createNewCinema(CinemaForm cinemaForm) {
        Objects.requireNonNull(cinemaForm, "Cinema form must not be null");
        var newCinema = cinemaRepository.saveAndFlush(toDomainObject(cinemaForm));
        return newCinema.getId();
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Optional<Cinema> findById(CinemaId id) {

        return cinemaRepository.findById(id);
    }

    @Override
    public void addCinemaHall(CinemaHallForm cinemaHallForm, CinemaId cinemaId) {
        CinemaHall cinemaHall = new CinemaHall(cinemaId,
                cinemaHallForm.getCapacity(),
        cinemaHallForm.getCinemaHallNumber(),
        cinemaHallForm.getNumberLoveSeats());
        Cinema cinema = this.cinemaRepository.findById(cinemaId).orElseThrow(CinemaIdNotFoundException::new);
        cinema.addCinemaHall(cinemaHall);
        this.cinemaRepository.saveAndFlush(cinema);
    }

    @Override
    public void removeCinemaHall(CinemaHallId cinemaHallId, CinemaId cinemaId) {
        Cinema cinema = findById(cinemaId).orElseThrow(CinemaIdNotFoundException::new);
        if(cinema.containsCinemaHall(cinemaHallId)){
            cinema.removeCinemaHall(cinemaHallId);
        }
        this.cinemaRepository.saveAndFlush(cinema);
    }

    @Override
    public List<CinemaHall> getCinemaHallsForCinema(CinemaId cinemaId) {
        Cinema cinema = findById(cinemaId).orElseThrow(CinemaIdNotFoundException::new);
        List<CinemaHall> cinemaHallList = new ArrayList<>(cinema.getCinemaHalls());
        return cinemaHallList;
    }

    @Override
    public void updateTotalTicketsSold(CinemaHallId cinemaHallId, int total) {
        for(Cinema cinema: findAll())
        {
            if(cinema.containsCinemaHall(cinemaHallId)){
                cinema.updateNumberShowsWatchedInCinemaHall(cinemaHallId, total);
            }
        }
    }

    private Cinema toDomainObject(CinemaForm cinemaForm) {
        var cinema = new Cinema(cinemaForm.getCinemaName(),
                cinemaForm.getLocation());
        return  cinema;
    }
}
