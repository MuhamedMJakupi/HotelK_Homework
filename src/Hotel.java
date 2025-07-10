import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void makeBooking(Booking booking) {
        // Check if room is available for requested period
        for (Booking b : bookings) {
            if (b.getRoom().equals(booking.getRoom()) &&
                    !(booking.getCheckOut().isBefore(b.getCheckIn()) || booking.getCheckIn().isAfter(b.getCheckOut()))) {
                System.out.println("Room not available for those dates.");
                return;
            }
        }

        bookings.add(booking);
        booking.getRoom().setAvailable(false);
        System.out.println("Booking successful: " + booking);
    }

    public void cancelBooking(String bookingID) {
        bookings.removeIf(b -> {
            if (b.getBookingID().equals(bookingID)) {
                b.getRoom().setAvailable(true);
                System.out.println("Booking canceled: " + bookingID);
                return true;
            }
            return false;
        });
    }

    public void showRooms() {
        System.out.println("Rooms:");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    public void showBookings() {
        System.out.println("Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }


    // will check again later
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            boolean isFree = true;
            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room) &&
                        !(checkOut.isBefore(booking.getCheckIn()) || checkIn.isAfter(booking.getCheckOut()))) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                available.add(room);
            }
        }
        return available;
    }

}
