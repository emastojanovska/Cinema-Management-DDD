package mk.ukim.finki.emt.sharedkernel.domain.events.movies;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
@Getter
public class MovieShowed extends DomainEvent {
    private int timesWatched;
    private String movieId;
    public MovieShowed(String topic) {
        super(TopicHolder.TOPIC_MOVIE_SHOWED);
    }

    public MovieShowed(int timesWatched, String movieId) {
        super(TopicHolder.TOPIC_MOVIE_SHOWED);
        this.timesWatched = timesWatched;
        this.movieId = movieId;
    }
}
