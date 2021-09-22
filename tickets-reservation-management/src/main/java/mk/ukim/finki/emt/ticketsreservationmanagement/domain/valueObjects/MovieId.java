package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;

import javax.persistence.Embeddable;

@Embeddable
public class MovieId extends DomainObjectId {
    protected MovieId() {
        super(MovieId.randomId(MovieId.class).getId());
    }

    public MovieId(@NonNull String uuid) {
        super(uuid);
    }
}
