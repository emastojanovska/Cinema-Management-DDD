package mk.ukim.finki.emt.cinemamanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class CinemaHallId extends DomainObjectId {
    private CinemaHallId() {
        super(CinemaHallId.randomId(CinemaHallId.class).getId());
    }

    public CinemaHallId(@NonNull String uuid) {
        super(uuid);
    }

    public static CinemaHallId of(String uuid) {
        CinemaHallId c = new CinemaHallId(uuid);
        return c;
    }
}
