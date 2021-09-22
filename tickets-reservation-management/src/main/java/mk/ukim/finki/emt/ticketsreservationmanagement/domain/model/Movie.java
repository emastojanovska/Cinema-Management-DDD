package mk.ukim.finki.emt.ticketsreservationmanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.TicketId;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="movie")
@Getter
public class Movie extends AbstractEntity<MovieId> {
    private String name;
    private String description;
    private String actors;
    private String director;
    private String image;
    private String trailer;
    private double rating;
    private Quantity numberTimesWatched;

    public Movie(){
        super(MovieId.randomId(MovieId.class));
    }

    public Movie(String name, String description, String actors, String director, String image, String trailer, double rating) {
        super(MovieId.randomId(MovieId.class));
        this.name = name;
        this.description = description;
        this.actors = actors;
        this.director = director;
        this.image = image;
        this.trailer = trailer;
        this.rating = rating;
        this.numberTimesWatched = new Quantity(0);
    }

    public void incrementTimesWatched(){
        this.numberTimesWatched.incrementQuantity();
    }

    public void updateNumberTimesWatched(int times){
        this.numberTimesWatched = new Quantity(times);
    }


}
