package mk.ukim.finki.emt.cinemamanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.SeatId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="seat")
@Getter
public class Seat extends AbstractEntity<SeatId> {
    boolean isLoveSeat;
    int seatNumber;
    public Seat(){
        super(SeatId.randomId(SeatId.class));
    }
    public Seat(boolean isLoveSeat){
        super(SeatId.randomId(SeatId.class));
        this.isLoveSeat = isLoveSeat;
    }
}
