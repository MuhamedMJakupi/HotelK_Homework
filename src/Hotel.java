import Exceptions.InvalidBookingDatesException;
import Exceptions.RoomUnavailableException;
import Interface.Bookable;
import Interface.Chargeable;
import Service.HotelService;
import Staff.Staff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Hotel {
    private final String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<HotelService> services = new ArrayList<>();
    private final List<Staff> staffMembers = new ArrayList<>();
    private final List<Chargeable> items = new ArrayList<>();



    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        items.add(room);
    }

    public void addService(HotelService service) {
        services.add(service);
        items.add(service);
    }

    public void addStaff(Staff staff) {
        staffMembers.add(staff);
    }


    // including exceptions both
    public void makeBooking(Booking booking) throws RoomUnavailableException, InvalidBookingDatesException {
        // Validate dates
        if (!booking.getCheckOut().isAfter(booking.getCheckIn())) {
            throw new InvalidBookingDatesException("Check-out date must be after check-in date.");
        }

        // Check room availability
        for (Booking b : bookings) {
            if (b.getRoom().equals(booking.getRoom()) &&
                    !(booking.getCheckOut().isBefore(b.getCheckIn()) || booking.getCheckIn().isAfter(b.getCheckOut()))) {
                throw new RoomUnavailableException("Room is not available for selected dates.");
            }
        }

        bookings.add(booking);

        booking.getRoom().setAvailable(false);

        if (booking.getRoom() instanceof Bookable bookableRoom) {
            bookableRoom.markAsBooked();
        }
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

    public void showServices() {
        System.out.println("Services:");
        for (HotelService service : services) {
            System.out.println(service);
        }
    }

    public void showStaff() {
        System.out.println("Staff:");
        for (Staff s : staffMembers) {
            System.out.println(s);
        }
    }


    public BigDecimal calculateTotalServiceCharges() {
        BigDecimal total = BigDecimal.ZERO;
        for (HotelService service : services) {
            total = total.add(service.getCost());
        }
        return total;
    }

    public BigDecimal calculateTotalBookingCharges() {
        BigDecimal total = BigDecimal.ZERO;
        for (Booking b : bookings) {
            total = total.add(b.getCost()); // This uses Booking’s getCost()
        }
        return total;
    }

}
