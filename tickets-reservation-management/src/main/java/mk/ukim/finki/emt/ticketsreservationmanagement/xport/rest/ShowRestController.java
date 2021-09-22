package mk.ukim.finki.emt.ticketsreservationmanagement.xport.rest;

import mk.ukim.finki.emt.sharedkernel.domain.finantial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ReservationId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.ShowId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Reservation;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Show;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHall;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHallId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.ShowService;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ReservationForm;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ShowForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController()
@RequestMapping("/api/shows")
@CrossOrigin(origins = "http://localhost:3000")
public class ShowRestController {
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private final ShowService showService;

    public ShowRestController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public List<Show> findAll(){
        return this.showService.findAll();
    }

    @GetMapping("/today")
    public List<Show> findTodayShows(){
        return this.showService.findTodayShows();
    }
    @GetMapping("/weekly")
    public List<Show> findThisWeekShows(){
        return this.showService.findThisWeekShows();
    }
    @GetMapping("/filter_weekly")
    public List<Show> filterShowsByDayOfWeek(@RequestParam String day){
        return this.showService.filterWeekShowsByDay(day.toUpperCase());
    }

    @GetMapping("/{id}/reservations")
    public List<Reservation> getReservationsForShow (@PathVariable String id){
        return this.showService.getReservationsForShow(ShowId.of(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Show> findById(@PathVariable String id)
    {
        return this.showService.findById(ShowId.of(id))
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("create_show")
    public ResponseEntity<Show> createShow(@RequestBody ShowForm showForm){
        return this.showService.createNewShow(showForm)
                .map(show -> ResponseEntity.ok().body(show))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{id}/reservation")
    public Reservation makeReservation(@PathVariable String id,
                                       @RequestParam String customerName,
                                       @RequestParam String telephone,
                                       @RequestParam(required = false) String currency,
                                       @RequestParam int ticketsNumber,
                                       @RequestParam int loveTicketsNumber){
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setCustomerName(customerName);
        reservationForm.setTelephone(telephone);
        reservationForm.setTicketsNumber(ticketsNumber);
        reservationForm.setLoveTicketsNumber(loveTicketsNumber);
        if(currency!=null){
            reservationForm.setCurrency(Currency.valueOf(currency.toUpperCase()));
        }else{
            reservationForm.setCurrency(Currency.MKD);
        }
        return this.showService.makeReservationForShow(ShowId.of(id), reservationForm);

    }
}
