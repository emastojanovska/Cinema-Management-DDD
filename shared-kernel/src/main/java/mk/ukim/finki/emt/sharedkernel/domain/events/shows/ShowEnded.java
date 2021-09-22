package mk.ukim.finki.emt.sharedkernel.domain.events.shows;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
@Getter
public class ShowEnded extends DomainEvent {

    private int totalTickets;
    private String cinemaHallId;

    public ShowEnded(String topic) {
        super(TopicHolder.TOPIC_SHOW_ENDED);
    }

    public ShowEnded(String cinemaHallId, int totalTickets) {
        super(TopicHolder.TOPIC_SHOW_ENDED);
        this.cinemaHallId = cinemaHallId;
        this.totalTickets = totalTickets;
    }
}
