package mk.ukim.finki.emt.ticketsreservationmanagement.domain.repository;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, TicketId> {
}
