package mk.ukim.finki.emt.cinemamanagement.services.forms;

import com.sun.istack.NotNull;
import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;

@Data
public class CinemaHallForm {
    @NotNull
    private int capacity;
    private int cinemaHallNumber;
    @NotNull
    private Quantity numberLoveSeats;
}
