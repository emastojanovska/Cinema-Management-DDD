package mk.ukim.finki.emt.cinemamanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class CinemaId extends DomainObjectId{
    private CinemaId() {
        super(CinemaId.randomId(CinemaId.class).getId());
    }

    public CinemaId(@NonNull String uuid) {
        super(uuid);
    }

    public static CinemaId of(String uuid) {
        CinemaId c = new CinemaId(uuid);
        return c;
    }
}
