package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class MovieId extends DomainObjectId {
    private MovieId() {
        super(MovieId.randomId(MovieId.class).getId());
    }

    public MovieId(@NonNull String uuid) {
        super(uuid);
    }

    public static MovieId of(String uuid) {
        MovieId m = new MovieId(uuid);
        return m;
    }
}
