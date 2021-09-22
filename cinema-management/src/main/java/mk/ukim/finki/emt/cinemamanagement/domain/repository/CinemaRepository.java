package mk.ukim.finki.emt.cinemamanagement.domain.repository;

import mk.ukim.finki.emt.cinemamanagement.domain.model.Cinema;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, CinemaId> {
}
