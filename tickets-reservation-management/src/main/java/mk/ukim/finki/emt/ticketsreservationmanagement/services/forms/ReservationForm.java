package mk.ukim.finki.emt.ticketsreservationmanagement.services.forms;

import com.sun.istack.NotNull;
import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Currency;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ticket;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ReservationForm {
    @NotNull
    private String customerName;
    @NotNull
    private String telephone;

    private Currency currency;
    @NotNull
    private int ticketsNumber;
    @NotNull
    private int loveTicketsNumber;


}
