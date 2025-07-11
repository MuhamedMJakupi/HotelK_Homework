package Service;

import java.math.BigDecimal;

public class LaundryService extends HotelService {
    private final int itemCount;

    public LaundryService(String description, BigDecimal baseCost, int itemCount) {
        super(description, baseCost);
        this.itemCount=itemCount;
    }


    @Override
    public BigDecimal getCost() {
        // Base cost + fixed handling fee
        //return getBaseCost().add(new BigDecimal("5.00"));

        // base cost + 2 per item
        return getBaseCost().add(BigDecimal.valueOf(itemCount * 2));

    }

    @Override
    public String toString() {
        return "LaundryService: " + getDescription() +
                " | Items: " + itemCount +
                " | Final Cost: " + getCost();
    }
}
