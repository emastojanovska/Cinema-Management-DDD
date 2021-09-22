package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;

@Getter
public class Movie implements ValueObject {
    private final MovieId id;
    private final String name;
    private final String description;
    private final String actors;
    private final String director;
    private final String image;
    private final String trailer;
    private final double rating;
    private final Quantity numberTimesWatched;

    public Movie(MovieId id, String name, String description, String actors, String director, String image, String trailer, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.actors = actors;
        this.director = director;
        this.image = image;
        this.trailer = trailer;
        this.rating = rating;
        this.numberTimesWatched = new Quantity(0);
    }

    private Movie(){
        this.id=MovieId.randomId(MovieId.class);
        this.name = "";
        this.description = "";
        this.actors = "";
        this.director = "";
        this.image = "";
        this.trailer  = "";
        this.rating = 0.0;
        this.numberTimesWatched = new Quantity(0);
    }

    @JsonCreator
    public Movie(@JsonProperty("id") MovieId movieId,
                 @JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("actors") String actors,
                 @JsonProperty("director") String director,
                 @JsonProperty("image") String image,
                 @JsonProperty("trailer") String trailer,
                 @JsonProperty("rating") double rating,
                 @JsonProperty("numberTimesWatched") Quantity numberTimesWatched){
        this.id = movieId;
        this.name = name;
        this.description = description;
        this.actors = actors;
        this.director = director;
        this.image = image;
        this.trailer = trailer;
        this.rating = rating;
        this.numberTimesWatched = numberTimesWatched;
    }
}

