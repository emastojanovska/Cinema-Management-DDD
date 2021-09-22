package mk.ukim.finki.emt.cinemamanagement.services.forms;

import com.sun.istack.NotNull;
import lombok.Data;
import mk.ukim.finki.emt.cinemamanagement.domain.model.CinemaHall;

import java.util.HashSet;
import java.util.Set;

@Data
public class CinemaForm {
    @NotNull
    private String cinemaName;
    @NotNull
    private String location;
    private Set<CinemaHallForm> cinemaHallForms = new HashSet<>();

}
