package mk.ukim.finki.emt.cinemamanagement.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.cinemamanagement.services.CinemaService;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.shows.ShowEnded;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CinemaEventListener {
    private final CinemaService cinemaService;
    @KafkaListener(topics = TopicHolder.TOPIC_SHOW_ENDED, groupId = "cinemaManagement")
    //Everytime when event for total tickets sold in a specific cinema hall is published,
    //this listener calls the updateTotalTicketsSold method from the cinemaService
    public void consumeShowEndedEvent(String jsonMessage){
        try{
            ShowEnded event = DomainEvent.fromJson(jsonMessage, ShowEnded.class);
            cinemaService.updateTotalTicketsSold(CinemaHallId.of(event.getCinemaHallId()),event.getTotalTickets());
        } catch (Exception e) {

        }
    }
}

