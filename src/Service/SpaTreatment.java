package Service;

import java.math.BigDecimal;

public class SpaTreatment extends HotelService {

    private final int durationMinutes;

    public SpaTreatment(String description, BigDecimal baseCost, int durationMinutes,String serviceName) {
        super(description, baseCost,serviceName);
        this.durationMinutes = durationMinutes;
    }

    @Override
    public BigDecimal getCost() {
        if (super.getCost() != null && super.getCost().compareTo(getBaseCost()) != 0) {
            return super.getCost();
        }
        return getBaseCost().add(BigDecimal.valueOf(durationMinutes * 0.5));
    }

    @Override
    public String toString() {
        return "SpaTreatment: " + getDescription() +
                " | Duration: " + durationMinutes + " mins" +
                " | Final Cost: " + getCost();
    }
}
