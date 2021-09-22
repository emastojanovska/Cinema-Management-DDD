package mk.ukim.finki.emt.cinemamanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class SeatId extends DomainObjectId {
    private SeatId() {
        super(SeatId.randomId(SeatId.class).getId());
    }

    public SeatId(@NonNull String uuid) {
        super(uuid);
    }
}
