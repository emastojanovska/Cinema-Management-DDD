package mk.ukim.finki.emt.cinemamanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="cinema_hall")
@Getter
public class CinemaHall extends AbstractEntity<CinemaHallId> {
    private CinemaId cinemaId;
    private int capacity;
    private int cinemaHallNumber;
    @AttributeOverride(name="quantity", column=@Column(name="number_shows"))
    private Quantity numberShows;
    @AttributeOverride(name="quantity", column=@Column(name="number_love_seats"))
    private Quantity numberLoveSeats;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Seat> seats = new HashSet<>();
    public CinemaHall(){
        super(CinemaHallId.randomId(CinemaHallId.class));
    }


    public CinemaHall(CinemaId cinemaId, int capacity, int cinemaHallNumber, Quantity numberLoveSeats){
        this.cinemaId = cinemaId;
        this.capacity = capacity;
        this.cinemaHallNumber = cinemaHallNumber;
        this.numberShows = new Quantity(0);
        //Instancing the love seats
        for(int i=0; i<numberLoveSeats.accessQuantity(); i++){
            Seat seat = new Seat(true);
            seats.add(seat);
        }
        //Instancing the regular seats
        for(int i=0; i<capacity - numberLoveSeats.accessQuantity(); i++){
            Seat seat = new Seat(false);
            seats.add(seat);
        }
    }

    public void numberShowsWatched(int numberShows){
        this.numberShows = new Quantity(numberShows);
    }
}
