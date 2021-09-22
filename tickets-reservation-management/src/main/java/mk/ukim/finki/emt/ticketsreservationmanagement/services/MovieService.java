package mk.ukim.finki.emt.ticketsreservationmanagement.services;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.MovieForm;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    MovieId createMovie(MovieForm movieForm);
    List<Movie> findAll();
    Optional<Movie> findById(MovieId id);
    void updateTimesWatched(MovieId movieId, int times);
}
