package service;

import common.interfaces.Chargeable;
import domain.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class HotelService implements Chargeable {
    private final String serviceID;
    private final String description;
    private  BigDecimal baseCost;
    private BigDecimal cost; //for after discount
    private User user;
    private String  serviceName;

    // Part 3: 5.2 -Homework 4
    private Map<String, Double> discountCodes = new HashMap<>();


    public HotelService(String description, BigDecimal baseCost,String serviceName) {
        this.serviceID = UUID.randomUUID().toString();
        this.description = description;
        this.baseCost = baseCost;
        this.serviceName = serviceName;
        discountCodes = new HashMap<>();
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

    public String getServiceName() {
        return serviceName;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }


    //public abstract BigDecimal getCost();


    @Override
    public BigDecimal getCost() {
        return cost != null ? cost : baseCost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

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


    // Method to add discount codes
    public void addDiscountCode(String code, Double discount) {
        discountCodes.put(code, discount);
    }

    // Apply discount by code
    // Part 3: 5.3 -Homework 4
    public void applyDiscount(String discountCode) {
        Double discount = discountCodes.get(discountCode);
        if (discount != null) {
            BigDecimal newCost = baseCost.multiply(BigDecimal.valueOf(1 - discount));
            setCost(newCost);
            System.out.println("Discount applied: " + discountCode + " | New cost: " + newCost);
        } else {
            System.out.println("Invalid discount code: " + discountCode);
        }
    }


}
