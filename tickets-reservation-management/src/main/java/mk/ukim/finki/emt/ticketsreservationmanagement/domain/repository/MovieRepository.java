package mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, MovieId> {
}
