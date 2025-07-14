package core;

import Interface.Bookable;
import Interface.Chargeable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Room implements Bookable, Chargeable {
    private final String roomID;
    private final RoomType type;
    private  BigDecimal nightlyRate;
    private boolean isAvailable;
    private Guest currentGuest;
    private String roomNumber;



    public Room(RoomType type, BigDecimal nightlyRate,String roomNumber) {
        this.roomID = UUID.randomUUID().toString(); // auto-generate
        this.type = type;
        this.nightlyRate = nightlyRate;
        this.isAvailable = true;
        this.roomNumber = roomNumber;
    }

    public Room() {
        this(RoomType.STANDARD, new BigDecimal("40.00"),"000");
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


    @Override
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        return isAvailable;
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




}
