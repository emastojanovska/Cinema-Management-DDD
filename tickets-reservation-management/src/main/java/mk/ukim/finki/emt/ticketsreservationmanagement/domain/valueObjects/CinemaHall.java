package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;

@Getter
public class CinemaHall implements ValueObject {
    private final CinemaHallId id;
    private final int capacity;
    private final int cinemaHallNumber;
    private final Quantity numberLoveSeats;
    private final Quantity numberShows;

   /* private CinemaHall() {
        this.id= CinemaHallId.randomId(CinemaHallId.class);
        this.capacity = 0;
        this.cinemaHallNumber = 0;
        this.numberLoveSeats = new Quantity(0);
        this.numberShows = new Quantity(0);
    }*/

    //This constructor is used for in memory database
    public CinemaHall() {
        this.id= CinemaHallId.randomId(CinemaHallId.class);
        this.capacity = 10;
        this.cinemaHallNumber = 1;
        this.numberLoveSeats = new Quantity(5);
        this.numberShows = new Quantity(5);
    }


    @JsonCreator
    public CinemaHall(@JsonProperty("id") CinemaHallId id,
                   @JsonProperty("capacity") int capacity,
                   @JsonProperty("cinemaHallNumber") int cinemaHallNumber,
                   @JsonProperty("numberLoveSeats") Quantity numberLoveSeats,
                   @JsonProperty("numberShows") Quantity numberShows) {
        this.id = id;
        this.capacity = capacity;
        this.cinemaHallNumber = cinemaHallNumber;
        this.numberLoveSeats = numberLoveSeats;
        this.numberShows = numberShows;
    }


}
