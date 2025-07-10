import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Grand Palace");

        // Add Rooms
        Room r1 = new Room(RoomType.STANDARD, new BigDecimal("50.00"));
        Room r2 = new Room(RoomType.DELUXE, new BigDecimal("80.00"));
        hotel.addRoom(r1);
        hotel.addRoom(r2);

        // Register Guests
        Guest g1 = new Guest( "Alice", "Brown", "alice@example.com");
        Guest g2 = new Guest( "Bob", "Smith", "bob@example.com");

        // Make Bookings
        Booking b1 = new Booking( r1, g1, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12));
        hotel.makeBooking(b1);
        System.out.println();

        // Attempt overlapping booking (should fail)
        Booking b2 = new Booking( r1, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
        hotel.makeBooking(b2);
        System.out.println();

        // Valid booking
        Booking b3 = new Booking( r2, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
        hotel.makeBooking(b3);
        System.out.println();

        // Cancel a booking
        hotel.cancelBooking(b1.getBookingID()); // correct
        System.out.println();


        // Show current state
        hotel.showRooms();
        System.out.println();
        hotel.showBookings();


        //will check again, Hotel.java
        System.out.println("\nAvailable rooms from 2025-07-10 to 2025-07-12:");
        for (Room r : hotel.getAvailableRooms(LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12))) {
            System.out.println(r);
        }

    }
}

