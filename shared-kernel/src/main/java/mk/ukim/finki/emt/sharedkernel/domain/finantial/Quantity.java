package mk.ukim.finki.emt.sharedkernel.domain.finantial;

import com.sun.istack.NotNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Quantity implements ValueObject {
    private final int quantity;

    protected Quantity(){
        this.quantity = 0;
    }

    public Quantity(@NotNull int quantity){
        this.quantity = quantity;
    }

    public static Quantity valueOf(int quantity){
        return new Quantity(quantity);
    }

    public int accessQuantity(){
        return this.quantity;
    }

    public int incrementQuantity(){
        return this.quantity + 1;
    }

    public int decrementQuantity(){
        if(this.quantity == 0){
            throw new IllegalStateException("Cannot decrement quantity if it is 0");
        }
        return this.quantity - 1;
    }





}
