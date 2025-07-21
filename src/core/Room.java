package core;

import Interface.Bookable;
import Interface.Chargeable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Room implements Bookable, Chargeable {
    private final String roomID;
    private final RoomType type;
    private  BigDecimal nightlyRate;
    private boolean isAvailable;
    private Guest currentGuest;
    private String roomNumber;

    // Part 3: - 3.1 - Homework 4
    private final Map<LocalDate, Boolean> occupancyMap = new HashMap<>();

    public Room(RoomType type, BigDecimal nightlyRate,String roomNumber) {
        this.roomID = UUID.randomUUID().toString(); // auto-generate
        this.type = type;
        this.nightlyRate = nightlyRate;
        this.isAvailable = true;
        this.roomNumber = roomNumber;
    }

    public Room() {
        this(RoomType.STANDARD, new BigDecimal("50.00"),"000");
    }


    public String getRoomID() {
        return roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public BigDecimal getNightlyRate() {
        return nightlyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room other = (Room) obj;
        return roomID.equals(other.roomID);
    }


    @Override
    public int hashCode() {
        return roomID.hashCode();
    }

    @Override
    public String toString() {
        return "Room{" + roomID + ", Number: " + roomNumber + ", " + type + ", Rate: " + nightlyRate + ", Available: " + isAvailable + "}";
    }


    // Part 3: 3.2 -Homework 4
    @Override
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
            if (occupancyMap.getOrDefault(date, false)) {
                return false;
            }
        }
        return true;
    }

    // Part 3: 3.3 -Homework 4
    public void markAsBooked(LocalDate checkIn, LocalDate checkOut) {
        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
            occupancyMap.put(date, true);
        }
        this.isAvailable = false;
    }
    public void unmarkAsBooked(LocalDate checkIn, LocalDate checkOut) {
        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
            occupancyMap.remove(date);
        }
        this.isAvailable = true;
    }




    @Override
    public void markAsBooked() {
        this.isAvailable = false;
    }

    @Override
    public BigDecimal getCost() {
        return nightlyRate;
    }

    //1. - Homework 3
    public boolean wasVacantForThreeDays(boolean[] occupancy) {
        int count = 0;
        for (boolean day : occupancy) {
            if (!day) {
                count++;
                if (count >= 3) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }

    public Guest getCurrentGuest() {
        return currentGuest;
    }

    public void setCurrentGuest(Guest guest) {
        this.currentGuest = guest;
    }
    public void setNightlyRate(BigDecimal rate) {
        this.nightlyRate = rate;
    }

    public int getBedCount() {
        return 0; // Default - subclasses can override
    }


    // Part 2: 1. -Homework 4
    public static List<Room> getRoomsAboveRate(List<Room> rooms, double minRate) {
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getNightlyRate().doubleValue() > minRate) {
                result.add(room);
            }
        }
        return result;
    }





}
