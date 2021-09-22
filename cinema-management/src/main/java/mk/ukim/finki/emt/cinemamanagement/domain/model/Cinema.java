package mk.ukim.finki.emt.cinemamanagement.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name="cinema")
@Getter
public class Cinema extends AbstractEntity<CinemaId> {
    private String cinemaName;
    private String location;
    @AttributeOverride(name="quantity", column=@Column(name="number_tickets"))
    private Quantity numberTickets;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CinemaHall> cinemaHalls = new HashSet<>();
    protected Cinema(){
        super(CinemaId.randomId(CinemaId.class));
    }
    public Cinema(String cinemaName, String location){
        this.numberTickets = new Quantity(0);
        this.cinemaName = cinemaName;
        this.location = location;
    }
    //This method is used to update the number of shows streamed in the cinema
    public void updateNumberTickets(Quantity quantity){
        this.numberTickets = quantity;
    }

    public CinemaHall addCinemaHall(CinemaHall cinemaHall){
        Objects.requireNonNull(cinemaHall, "Cinema hall must not be null");
        cinemaHalls.add(cinemaHall);
        return cinemaHall;
    }

    //This method is used to update the number of shows streamed in some specific cinema hall
    public void updateNumberShowsWatchedInCinemaHall(CinemaHallId cinemaHallId, int numberShows)
    {
        for (CinemaHall cinemaHall: cinemaHalls)
        {
            if(cinemaHall.getId().equals(cinemaHallId))
            {
                cinemaHall.numberShowsWatched(numberShows);
            }
        }
    }

    //Returns true if specific cinema hall is located in this cinema
    public boolean containsCinemaHall(CinemaHallId cinemaHallId){
        for (CinemaHall cinemaHall: cinemaHalls)
        {
            if(cinemaHall.getId().equals(cinemaHallId))
            {
                return true;
            }
        }
        return false;
    }

    //Removes the cinema hall from this cinema
    public void removeCinemaHall(@NotNull CinemaHallId cinemaHallId){
        cinemaHalls.removeIf(c -> c.getId().equals(cinemaHallId));
    }
}
