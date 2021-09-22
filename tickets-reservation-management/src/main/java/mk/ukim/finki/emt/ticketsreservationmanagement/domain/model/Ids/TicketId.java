package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class TicketId extends DomainObjectId{
    private TicketId() {
        super(TicketId.randomId(TicketId.class).getId());
    }

    public TicketId(@NonNull String uuid) {
        super(uuid);
    }
}
