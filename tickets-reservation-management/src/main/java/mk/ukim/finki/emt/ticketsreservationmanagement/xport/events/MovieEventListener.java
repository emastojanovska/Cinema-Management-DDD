package mk.ukim.finki.emt.ticketsreservationmanagement.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.movies.MovieShowed;
import mk.ukim.finki.emt.sharedkernel.domain.events.shows.ShowEnded;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.MovieService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieEventListener {
    private final MovieService movieService;
    @KafkaListener(topics = TopicHolder.TOPIC_MOVIE_SHOWED, groupId = "cinemaManagement")
    //Everytime the number of times some movie was streamed is updated, the method updatesTimesWatched is called
    public void consumeShowEndedEvent(String jsonMessage){
        try{
            MovieShowed event = DomainEvent.fromJson(jsonMessage, MovieShowed.class);
            movieService.updateTimesWatched(MovieId.of(event.getMovieId()), event.getTimesWatched());
        } catch (Exception e) {

        }
    }
}
