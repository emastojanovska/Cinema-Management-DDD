package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class CinemaHallId extends DomainObjectId {
    protected CinemaHallId() {
        super(CinemaHallId.randomId(CinemaHallId.class).getId());
    }
    public CinemaHallId(@NonNull String uuid) {
        super(uuid);
    }
}
