package core;

import java.math.BigDecimal;

public class StandardRoom extends Room {
    public StandardRoom(RoomType type, BigDecimal rate,String roomNumber) {
        super(RoomType.STANDARD, rate,roomNumber);
    }

    //4. - Homework 3
    public void applyDiscounts(char[] codes) {
        for (char code : codes) {
            switch (code) {
                case 'A':
                    setNightlyRate(getNightlyRate().multiply(new BigDecimal("0.90"))); // 10% off
                    break;
                case 'B':
                    setNightlyRate(getNightlyRate().multiply(new BigDecimal("0.80"))); // 20% off
                    break;
                case 'C':
                    setNightlyRate(getNightlyRate().multiply(new BigDecimal("0.75"))); // 25% off
                    break;
                default:
                    System.out.println("Unknown discount code: " + code);
            }

        }
    }
}
