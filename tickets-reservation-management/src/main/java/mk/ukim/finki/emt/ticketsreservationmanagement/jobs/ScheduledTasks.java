package mk.ukim.finki.emt.ticketsreservationmanagement.jobs;

import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.ShowService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final ShowService showService;

    //Every half an hour updates the shows to see if they are ended or not
    public ScheduledTasks(ShowService showService) {
        this.showService = showService;
    }
    @Scheduled(cron="0 0/30 * * * ?")
    public void refreshShowsView(){
        this.showService.refreshShows();
    }

    //Every two hours updates the number of sold tickets in specific cinema hall
    @Scheduled(cron = " 0 0 */2 * * ?")
    public void updateNumberSoldTicketsInCinemaHall(){
        this.showService.numberTicketsSoldInCinemaHall();
    }
}
