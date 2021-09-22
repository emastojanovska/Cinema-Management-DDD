package mk.ukim.finki.emt.ticketsreservationmanagement.domain.valueObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import java.time.LocalDateTime;
@Getter
public class Date implements ValueObject {
    private final LocalDateTime dateTime;

    protected Date(){
        this.dateTime = LocalDateTime.now();
    }

    public Date(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    public boolean equal(LocalDateTime localDateTime)
    {
        if(localDateTime.getDayOfYear() == dateTime.getDayOfYear()
        && localDateTime.getYear() == dateTime.getYear())
            return true;
        else
            return false;
    }



}
