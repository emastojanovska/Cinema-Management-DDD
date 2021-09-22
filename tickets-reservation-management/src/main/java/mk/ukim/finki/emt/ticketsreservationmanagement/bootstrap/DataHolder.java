package mk.ukim.finki.emt.ticketsreservationmanagement.bootstrap;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.finantial.Money;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.exceptions.MovieIdNotExistsException;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Ids.MovieId;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.model.Movie;
import mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.CinemaHall;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.MovieService;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.ShowService;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.MovieForm;
import mk.ukim.finki.emt.ticketsreservationmanagement.services.forms.ShowForm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@Getter
public class DataHolder {
    private final MovieService movieService;
    private final ShowService showService;


    public DataHolder(MovieService movieService, ShowService showService) {
        this.movieService = movieService;
        this.showService = showService;
    }

    //String name, String description, String actors, String director, String image, String trailer, double rating
    @PostConstruct
    public void initData(){
        MovieForm movieForm = new MovieForm();
        movieForm.setName("The Age of Adaline");
        movieForm.setDescription("Romance, fantasy");
        movieForm.setActors("Blake Lively, Harrison Ford");
        movieForm.setDirector("Lee Toland Krieger");
        movieForm.setImage("https://upload.wikimedia.org/wikipedia/en/thumb/3/33/The_Age_of_Adaline_film_poster.png/220px-The_Age_of_Adaline_film_poster.png");
        movieForm.setTrailer("https://www.youtube.com/watch?v=7UzSekc0LoQ");
        movieForm.setRating(4.5);

        MovieForm movieForm1 = new MovieForm();
        movieForm1.setName("Malcolm and Marie");
        movieForm1.setDescription("Drama");
        movieForm1.setActors("Zendaya, John David Washington");
        movieForm1.setDirector("Sam Levinson");
        movieForm1.setImage("https://m.media-amazon.com/images/M/MV5BYjVkMmU1NGItZjM4MC00ODM1LWEyOTEtY2Y1NTg0YjRhYjEwXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg");
        movieForm1.setTrailer("https://www.youtube.com/watch?v=CGZmwsK58M8");
        movieForm1.setRating(4.6);

        MovieId movieId1 = this.movieService.createMovie(movieForm1);
        MovieId movieId = this.movieService.createMovie(movieForm);

        Movie movie1 = this.movieService.findById(movieId1).orElseThrow(MovieIdNotExistsException::new);
        Movie movie2 = this.movieService.findById(movieId).orElseThrow(MovieIdNotExistsException::new);

        mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId movieIdValueObject = new mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId(movieId1.toString());
        mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie movieValueObject =
                new mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie(movieIdValueObject,
                        movie1.getName(), movie1.getDescription(), movie1.getActors(),
                        movie1.getDirector(), movie1.getImage(), movie1.getTrailer(),
                        movie1.getRating());
        mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId movieIdValueObject1 = new mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.MovieId(movieId.toString());
        mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie movieValueObject1 =
                new mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects.Movie(movieIdValueObject1,
                        movie2.getName(), movie2.getDescription(), movie2.getActors(),
                        movie2.getDirector(), movie2.getImage(), movie2.getTrailer(),
                        movie2.getRating());
        CinemaHall cinemaHall = new CinemaHall();

        ShowForm showForm = new ShowForm(LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(105L),
                new Money(Currency.MKD, 180),
                cinemaHall,
                movieValueObject
                );
        ShowForm showForm1 = new ShowForm(LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(5),
                new Money(Currency.MKD, 180),
                cinemaHall,
                movieValueObject1
        );
        ShowForm showForm2 = new ShowForm(LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                new Money(Currency.MKD, 180),
                cinemaHall,
                movieValueObject
        );
        ShowForm showForm3 = new ShowForm(LocalDateTime.now().plusDays(2).plusHours(3),
                LocalDateTime.now().plusDays(2).plusHours(5),
                new Money(Currency.MKD, 180),
                cinemaHall,
                movieValueObject1
        );
        ShowForm showForm4 = new ShowForm(LocalDateTime.now().minusHours(5),
                LocalDateTime.now().minusHours(3),
                new Money(Currency.MKD, 180),
                cinemaHall,
                movieValueObject1
        );
        this.showService.createNewShow(showForm);
        this.showService.createNewShow(showForm1);
        this.showService.createNewShow(showForm2);
        this.showService.createNewShow(showForm3);
        this.showService.createNewShow(showForm4);
    }
}
