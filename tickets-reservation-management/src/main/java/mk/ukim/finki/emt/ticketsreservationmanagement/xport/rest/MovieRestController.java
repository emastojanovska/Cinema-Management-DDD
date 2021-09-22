package mk.ukim.finki.emt.ticketsreservationmanagement.xport.rest;

import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieRestController {

    private final MovieService movieService;

    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping
    public List<Movie> findAll(){
        return this.movieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id)
    {
        return this.movieService.findById(MovieId.of(id))
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
