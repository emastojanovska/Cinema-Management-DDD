package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;

import javax.persistence.Embeddable;

@Embeddable
public class ShowId extends DomainObjectId {
    protected ShowId() {
        super(ShowId.randomId(ShowId.class).getId());
    }

    public ShowId(@NonNull String uuid) {
        super(uuid);
    }


}
