package mk.ukim.finki.emt.cinemamanagement.xport.rest;

import mk.ukim.finki.emt.cinemamanagement.domain.model.Cinema;
import mk.ukim.finki.emt.cinemamanagement.domain.model.CinemaHall;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaHallId;
import mk.ukim.finki.emt.cinemamanagement.domain.model.Ids.CinemaId;
import mk.ukim.finki.emt.cinemamanagement.services.CinemaService;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaForm;
import mk.ukim.finki.emt.cinemamanagement.services.forms.CinemaHallForm;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Quantity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/cinema")
@CrossOrigin(origins = "http://localhost:3000")
public class CinemaController {

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public List<Cinema> findAll(){
        return this.cinemaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> findById(@PathVariable String id)
    {
        return this.cinemaService.findById(CinemaId.of(id))
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create_cinema")
    public void createNewCinema(@RequestParam String name,
                                @RequestParam String location){
        CinemaForm cinemaForm = new CinemaForm();
        cinemaForm.setCinemaName(name);
        cinemaForm.setLocation(location);
        cinemaService.createNewCinema(cinemaForm);
    }

    @GetMapping("/{id}/cinema_halls")
    public List<CinemaHall> findAllByCinema(@PathVariable String id){
        return this.cinemaService.getCinemaHallsForCinema(CinemaId.of(id));
    }

    @PostMapping("/{id}/add_cinema_hall")
    public void addCinemaHallToCinema(@PathVariable String id,
                                      @RequestParam String capacity,
                                      @RequestParam String cinemaHallNumber,
                                      @RequestParam String numberLoveSeats){
        CinemaHallForm cinemaHallForm = new CinemaHallForm();
        cinemaHallForm.setCinemaHallNumber(Integer.parseInt(cinemaHallNumber));
        cinemaHallForm.setCapacity(Integer.parseInt(capacity));
        cinemaHallForm.setNumberLoveSeats(new Quantity(Integer.parseInt(numberLoveSeats)));
        cinemaService.addCinemaHall(cinemaHallForm, CinemaId.of(id));
    }

    @DeleteMapping("/{id}/remove_cinema_hall")
    public void deleteCinemaHallFromCinema(@PathVariable String id,
                                           @RequestParam String cinemaHallId){
        cinemaService.removeCinemaHall(CinemaHallId.of(cinemaHallId),
                CinemaId.of(id));
    }
}
