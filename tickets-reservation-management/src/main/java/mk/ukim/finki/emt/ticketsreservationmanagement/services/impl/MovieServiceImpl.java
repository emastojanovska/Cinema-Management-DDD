package mk.ukim.finki.emt.ticketsreservationmanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.MovieIdNotExistsException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository.MovieRepository;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.MovieService;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.MovieForm;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ReservationForm;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final Validator validator;

    @Override
    public MovieId createMovie(MovieForm movieForm) {
        Objects.requireNonNull(movieForm,"Movie form must not be null.");
        var constraintViolations = validator.validate(movieForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The movie form is not valid", constraintViolations);
        }
        var newMovie = movieRepository.saveAndFlush(toDomainObject(movieForm));
        return newMovie.getId();
    }

    @Override
    public List<Movie> findAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findById(MovieId id) {
        return this.movieRepository.findById(id);
    }

    @Override
    //This method updates the number of times specific movie was streamed
    public void updateTimesWatched(MovieId movieId, int times) {
        Movie movie = findById(movieId).orElseThrow(MovieIdNotExistsException::new);
        movie.updateNumberTimesWatched(times);
    }

    private Movie toDomainObject(MovieForm movieForm) {
        String trailer = movieForm.getTrailer()
                .replace("watch?v=", "embed/");
        var movie = new Movie(movieForm.getName(),
                movieForm.getDescription(),
                movieForm.getActors(),
                movieForm.getDirector(),
                movieForm.getImage(),
                trailer,
                movieForm.getRating());
        return movie;
    }
}
