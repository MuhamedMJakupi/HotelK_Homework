import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Booking {
    private final String bookingID;
    private final Room room;
    private final Guest guest;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Booking(Room room, Guest guest, LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in.");
        }
        this.bookingID = UUID.randomUUID().toString(); // This generates a unique ID
        this.room = room;
        this.guest = guest;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getBookingID() { return bookingID; }
    public Room getRoom() { return room; }
    public Guest getGuest() { return guest; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }



    public BigDecimal calculateTotalCost() {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return room.getNightlyRate().multiply(BigDecimal.valueOf(nights));
    }

    @Override
    public String toString() {
        return "Booking{" + bookingID + ", Room: " + room.getRoomID() + ", Guest: " + guest.getFullName()
                + ", From: " + checkIn + " To: " + checkOut + ", Total: " + calculateTotalCost() + "}";
    }

    //diff version
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Booking other)) return false;
//        return bookingID.equals(other.bookingID);
//    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Booking other = (Booking) obj;
        return bookingID.equals(other.bookingID);
    }


    @Override
    public int hashCode() {
        return bookingID.hashCode();
    }



}
