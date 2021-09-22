package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class ReservationId extends DomainObjectId {
    private ReservationId() {
        super(ReservationId.randomId(ReservationId.class).getId());
    }

    public ReservationId(@NonNull String uuid) {
        super(uuid);
    }

    public static ReservationId of(String uuid) {
        ReservationId s = new ReservationId(uuid);
        return s;
    }

}
