package mk.ukim.finki.emt.ticketsreservationmanagement.services.forms;

import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHall;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ShowForm {
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @NotNull
    private CinemaHall cinemaHall;
    @NotNull
    private Money price;
    @NotNull
    private Movie movie;

    public ShowForm(LocalDateTime startDateTime,
                    LocalDateTime endDateTime,
                    Money price,
                    CinemaHall cinemaHall,
                    Movie movie) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.cinemaHall = cinemaHall;
        this.price = price;
        this.movie = movie;
    }
}
