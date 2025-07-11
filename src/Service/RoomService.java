package Service;

import java.math.BigDecimal;

public class RoomService extends HotelService {
    private final BigDecimal serviceFee;
    public RoomService(String description, BigDecimal baseCost, BigDecimal serviceFee) {
        super(description, baseCost);
        this.serviceFee = serviceFee;
    }

    @Override
    public BigDecimal getCost() {
        return getBaseCost().add(serviceFee); // flat fee for service
    }

    @Override
    public String toString() {
        return "RoomService: " + getDescription() +
                " | Service Fee: " + serviceFee +
                " | Final Cost: " + getCost();
    }
}
