package App.Model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CoffeeShopDTO{

    private String name;
    private String city;
    private String state;
    private String fullAddress;
    private boolean outlets;
    private boolean bathrooms;
    private boolean outdoorSeating;
    private boolean cashOk;


    public boolean getOutlets() {
        return outlets;
    }

    public boolean getBathrooms() {
        return bathrooms;
    }

    public boolean getOutdoorSeating() {
        return outdoorSeating;
    }

    public boolean getCashOk() {
        return cashOk;
    }
}
