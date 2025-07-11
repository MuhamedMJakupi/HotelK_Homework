import Interface.Bookable;
import Interface.Chargeable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//Room implemets bookable
public class Room implements Bookable, Chargeable {
    private final String roomID;
    private final RoomType type;
    private final BigDecimal nightlyRate;
    private boolean isAvailable;

    public Room(RoomType type, BigDecimal nightlyRate) {
        this.roomID = UUID.randomUUID().toString(); // auto-generate
        this.type = type;
        this.nightlyRate = nightlyRate;
        this.isAvailable = true;
    }

    public Room() {
        this(RoomType.STANDARD, new BigDecimal("40.00"));
    }


    public String getRoomID() {
        return roomID;
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
        return "Room{" + roomID + ", " + type + ", Rate: " + nightlyRate + ", Available: " + isAvailable + "}";
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
}
