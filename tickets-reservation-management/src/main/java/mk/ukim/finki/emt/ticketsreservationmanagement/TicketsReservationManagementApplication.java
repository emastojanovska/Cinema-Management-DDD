package mk.ukim.finki.emt.ticketsreservationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicketsReservationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsReservationManagementApplication.class, args);
    }

}
