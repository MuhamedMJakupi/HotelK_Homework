package core;

import Interface.Bookable;
import Interface.Chargeable;

import java.math.BigDecimal;
import java.util.UUID;

public class DeluxeRoom extends Room implements Chargeable, Bookable {
    private final int bedCapacity;

    public DeluxeRoom(BigDecimal nightlyRate, int bedCapacity, String roomNumber) {
        super(RoomType.DELUXE, nightlyRate, roomNumber);
        this.bedCapacity = bedCapacity;
    }

//    public int getBedCapacity() {
//        return bedCapacity;
//    }
    @Override
    public int getBedCount() {
        return bedCapacity;
    }


    //10. -Homework 3
    public boolean checkOccupancyViolation(int[] occupancyPerNight) {
        for (int nightOccupancy : occupancyPerNight) {
            if (nightOccupancy > bedCapacity) {
                return true;
            }
        }
        return false;
    }

    public boolean exceedsCapacity(int[] bedsPerNight, int maxCapacity) {
        for (int count : bedsPerNight) {
            if (count > maxCapacity)
                return true;
        }
        return false;
    }

    @Override
    public BigDecimal getCost() {
        return getNightlyRate(); // Simple flat cost; could be adjusted with premium logic
    }

    //needs method getRoomType();, already have a getType().
    @Override
    public String toString() {
        return "DeluxeRoom{" +
                "ID=" + getRoomID() +
                ", Type=" + getType() +
                ", Rate=" + getNightlyRate() +
                ", Capacity=" + bedCapacity +
                ", Available=" + isAvailable() +
                '}';
    }
}
