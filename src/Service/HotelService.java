package Service;

import Interface.Chargeable;
import core.User;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class HotelService implements Chargeable {
    private final String serviceID;
    private final String description;
    private final BigDecimal baseCost;
    private User user;



    public HotelService(String description, BigDecimal baseCost) {
        this.serviceID = UUID.randomUUID().toString();
        this.description = description;
        this.baseCost = baseCost;
    }

    public User getUser() {
        return user;
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

    //15. -Homework 3
    public void validateDiscountCodes(char[] codes) {
        for (char c : codes) {
            if (c < 'A' || c > 'Z') {
                System.out.println("Invalid code: " + c);
            }
        }
    }

    //19. -Homework 3
    public static void applyTierDiscounts(double[] costs, char[] tiers) {
        for (int i = 0; i < costs.length; i++) {
            char tier = tiers[i];
            double discount = 1.0;

            switch (tier) {
                case 'A':
                    discount = 0.90;
                    break;
                case 'B':
                    discount = 0.80;
                    break;
                case 'C':
                    discount = 0.70;
                    break;
                default:
                    System.out.println("Invalid tier: " + tier);
                    break;
            }
            double finalCost = costs[i] * discount;
            System.out.println("Original: " + costs[i] + ", Final: " + finalCost);
        }
    }


}
