package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="reservation")
@Getter
public class Reservation extends AbstractEntity<ReservationId>{
    private ShowId showId;
    private String customerName;
    private String telephone;
    private LocalDateTime reservationDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();
    @Column(name="reservation_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private int numberTickets;
    private int numberLoveTickets;

    protected Reservation(){
        super(ReservationId.randomId(ReservationId.class));
    }

    public Reservation(Currency currency, String customerName, String telephone){
        super(ReservationId.randomId(ReservationId.class));
        this.reservationDate = LocalDateTime.now();
        this.customerName = customerName;
        this.telephone = telephone;
        this.currency = currency;
    }

    public Reservation(ShowId showId,
                       Currency currency,
                       String customerName,
                       String telephone,
                       int numberTickets,
                       int numberLoveTickets){
        super(ReservationId.randomId(ReservationId.class));
        this.showId = showId;
        this.reservationDate = LocalDateTime.now();
        this.customerName = customerName;
        this.telephone = telephone;
        this.currency = currency;
        this.numberTickets = numberTickets;
        this.numberLoveTickets = numberLoveTickets;
    }

    public void addTicket(Ticket ticket){
        Objects.requireNonNull(ticket);
        this.tickets.add(ticket);
    }

    public List<Ticket> getTickets(){
        List<Ticket> tickets = new ArrayList<>(this.tickets);
        return tickets;
    }

    public void deleteTicket(@NonNull TicketId ticketId){
        Objects.requireNonNull(ticketId);
        tickets.removeIf(t -> t.getId().equals(ticketId));
    }

    public Money totalPrice(){
        return tickets.stream()
                .map(ticket -> ticket.getPrice())
                .reduce(new Money(currency, 0), Money::add);
    }

}
