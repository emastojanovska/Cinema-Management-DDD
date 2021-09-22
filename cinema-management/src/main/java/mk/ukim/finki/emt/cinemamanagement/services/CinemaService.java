package mk.ukim.finki.emt.cinemamanagement.services;

import mk.ukim.finki.emt.cinemamanagement.domain.model.Cinema;
import mk.ukim.finki.emt.cinemamanagement.domain.model.CinemaHall;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaForm;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaHallForm;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    CinemaId createNewCinema(CinemaForm cinemaForm);
    List<Cinema> findAll();
    Optional<Cinema> findById(CinemaId id);
    void addCinemaHall(CinemaHallForm cinemaHallForm, CinemaId cinemaId);
    void removeCinemaHall(CinemaHallId cinemaHallId, CinemaId cinemaId);
    List<CinemaHall> getCinemaHallsForCinema(CinemaId cinemaId);
    void updateTotalTicketsSold(CinemaHallId cinemaHallId, int total);
}
