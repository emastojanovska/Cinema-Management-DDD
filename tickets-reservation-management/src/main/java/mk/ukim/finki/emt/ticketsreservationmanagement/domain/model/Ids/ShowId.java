package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class ShowId extends DomainObjectId{
    private ShowId() {
        super(ReservationId.randomId(ShowId.class).getId());
    }

    public ShowId(@NonNull String uuid) {
        super(uuid);
    }

    public static ShowId of(String uuid) {
        ShowId s = new ShowId(uuid);
        return s;
    }
}
