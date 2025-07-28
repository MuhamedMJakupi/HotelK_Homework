package service;

import java.math.BigDecimal;

public class LaundryService extends HotelService {
    private final int itemCount;

    public LaundryService(String description, BigDecimal baseCost, int itemCount,String serviceName) {
        super(description, baseCost,serviceName);
        this.itemCount=itemCount;
    }


    @Override
    public BigDecimal getCost() {
        // Base cost + fixed handling fee
        //return getBaseCost().add(new BigDecimal("5.00"));

        // base cost + 2 per item
        //return getBaseCost().add(BigDecimal.valueOf(itemCount * 2));

        BigDecimal discounted = super.getCost();
        // If a discounted cost was set and it's not equal to baseCost, use it
        if (discounted != null && discounted.compareTo(getBaseCost()) != 0) {
            return discounted;
        }
        // Otherwise use the original calculation
        return getBaseCost().add(BigDecimal.valueOf(itemCount * 2));

    }

    @Override
    public String toString() {
        return "LaundryService: " + getDescription() +
                " | Items: " + itemCount +
                " | Final Cost: " + getCost();
    }

    //11. -Homework 3
    public void checkWeightLimit(double[] weights) {
        double total = 0;
        for (double w : weights)
            total += w;
        if (total > 20.0)
            System.out.println("Total weight exceeds 20kg!");
    }

}
