package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model;

import lombok.Getter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;

@Entity
@Getter
@Table(name="ticket")
public class Ticket extends AbstractEntity<TicketId> {
    private boolean isLoveTicket;
    private Money price;
    private boolean isAvailable;

    @AttributeOverride(name = "id", column = @Column(name = "show_id", nullable = false))
    private ShowId showId;
    public Ticket() {
        super(TicketId.randomId(TicketId.class));
    }

    public Ticket(boolean isLoveTicket, Money money, ShowId showId) {
        super(TicketId.randomId(TicketId.class));
        this.isLoveTicket = isLoveTicket;
        this.price = money;
        this.showId = showId;
        this.isAvailable = true;
    }

    public void isBought(){
        this.isAvailable = false;
    }
}
