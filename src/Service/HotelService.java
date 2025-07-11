package Service;

import Interface.Chargeable;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class HotelService implements Chargeable {
    private final String serviceID;
    private final String description;
    private final BigDecimal baseCost;



    public HotelService(String description, BigDecimal baseCost) {
        this.serviceID = UUID.randomUUID().toString();
        this.description = description;
        this.baseCost = baseCost;
    }


    public String getServiceID() {
        return serviceID;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public abstract BigDecimal getCost();

    @Override
    public String toString() {
        return "Service{" + serviceID + ", " + description + ", Base Cost: " + baseCost + "}";
    }
}
