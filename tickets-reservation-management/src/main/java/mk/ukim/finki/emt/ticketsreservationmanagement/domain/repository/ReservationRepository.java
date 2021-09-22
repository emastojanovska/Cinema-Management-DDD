package mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
}
