package Service;

import java.math.BigDecimal;

public class SpaTreatment extends HotelService {

    private final int durationMinutes;

    public SpaTreatment(String description, BigDecimal baseCost, int durationMinutes) {
        super(description, baseCost);
        this.durationMinutes = durationMinutes;
    }

    @Override
    public BigDecimal getCost() {
        return getBaseCost().add(BigDecimal.valueOf(durationMinutes*0.5
        )); // 20% fee
    }

    @Override
    public String toString() {
        return "SpaTreatment: " + getDescription() +
                " | Duration: " + durationMinutes + " mins" +
                " | Final Cost: " + getCost();
    }
}
