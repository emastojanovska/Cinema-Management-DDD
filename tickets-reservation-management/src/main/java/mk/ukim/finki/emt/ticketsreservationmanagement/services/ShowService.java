package mk.ukim.finki.emt.ticketsreservationmanagement.services;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.ReservationIdNotExistException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.TicketIdNotExistException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Show;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHallId;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ReservationForm;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ShowForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ShowService {
    Optional<Show> createNewShow(ShowForm showForm);
    List<Show> findAll();
    Optional<Show> findById(ShowId id);
    double totalTicketsPrice(ShowId id);
    void refreshShows();
    List<Show> findEndedShows();
    void numberTicketsSoldInCinemaHall();
    void numberTimesMoviePlayed();
    Reservation makeReservationForShow(ShowId showId, ReservationForm reservationForm);
    List<Show> findTodayShows();
    List<Show> findThisWeekShows();
    List<Show> filterWeekShowsByDay(String day);
    List<Reservation> getReservationsForShow(ShowId showId);

}
