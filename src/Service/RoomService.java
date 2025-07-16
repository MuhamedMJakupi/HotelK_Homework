package Service;

import java.math.BigDecimal;

public class RoomService extends HotelService {
    private final BigDecimal serviceFee;
    public RoomService(String description, BigDecimal baseCost, BigDecimal serviceFee,String serviceName) {
        super(description, baseCost,serviceName);
        this.serviceFee = serviceFee;
    }

    @Override
    public BigDecimal getCost() {
        if (super.getCost() != null && super.getCost().compareTo(getBaseCost()) != 0) {
            return super.getCost();
        }
        return getBaseCost().add(serviceFee);
    }

    @Override
    public String toString() {
        return "RoomService: " + getDescription() +
                " | Service Fee: " + serviceFee +
                " | Final Cost: " + getCost();
    }

    //12. -Homework 3
    public void completeSteps(char[] steps) {
        int i = 0;
        while (i < steps.length) {
            steps[i] = 'X';
            i++;
        }
    }

}
