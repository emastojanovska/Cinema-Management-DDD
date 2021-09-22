package mk.ukim.finki.emt.ticketsreservationmanagement.services.forms;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class MovieForm {
    @NotNull
    private String name;
    private String description;
    private String actors;
    @NotNull
    private String director;
    @NotNull
    private String image;
    private String trailer;
    @NotNull
    private double rating;
}
