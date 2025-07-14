package core;

import Exceptions.*;
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

    // added for duplicate method will check again,no need for now
    /*
     public void addRoom(Room room) {
    try {
        checkForDuplicateRoom(room);
        rooms.add(room);
    } catch (DuplicateRoomException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
*/


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
                throw new RoomUnavailableException("core.Room is not available for selected dates.");
            }
        }

        bookings.add(booking);

        booking.getRoom().setAvailable(false);

        if (booking.getRoom() instanceof Bookable bookableRoom) {
            bookableRoom.markAsBooked();
        }
        booking.getRoom().setCurrentGuest(booking.getGuest());

        System.out.println("core.Booking successful: " + booking);

    }


    public void cancelBooking(String bookingID) {
        bookings.removeIf(b -> {
            if (b.getBookingID().equals(bookingID)) {
                b.getRoom().setAvailable(true);
                System.out.println("core.Booking canceled: " + bookingID);
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
            total = total.add(b.getCost()); // This uses core.Booking’s getCost()
        }
        return total;
    }



    //14. -Homework 3
    public void checkRoomPricing(core.Room[] rooms) {
        for (core.Room r : rooms) {
            if (r.getNightlyRate().compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid rate for room: " + r.getRoomNumber());
            }
        }
    }

    //will check again needs getID(), had a getGuestId()
    //5. -Homework 3
    public void printUserBookings(core.User user) {
        for (core.Booking b : bookings) {
            if (b.getGuest().getGuestID().equals(user.getId())) {
                System.out.println(b);
            }
        }
    }

    //7. needs method getUser(), added to HotelService
    //7. - Homework 3
    public BigDecimal getServiceCostForUser(User user) {
        BigDecimal total = BigDecimal.ZERO;
        for (HotelService service : services) {
            if (service.getUser() != null && service.getUser().getId().equals(user.getId())) {
                total = total.add(service.getCost());
            }
        }
        return total;
    }



    // created new method for roomNumber in Room.java
    //13. -Homework 3
    public void checkForDuplicateRoom(Room newRoom) throws DuplicateRoomException {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(newRoom.getRoomNumber())) {
                throw new DuplicateRoomException("Duplicate Room ID detected: " + newRoom.getRoomNumber());
            }
        }
    }

    //17. -Homework 3
    public void checkRoomCapacity(Room[] rooms, int maxCapacity) throws RoomCapacityExceededException {
        for (Room room : rooms) {
            if (room.getBedCount() > maxCapacity) {
                throw new RoomCapacityExceededException("Room " + room.getRoomNumber() + " exceeds capacity.");
            }
        }
    }

    //20. -Homework 3
    public void checkForDuplicateGuestBookings(Room[] rooms) throws DuplicateGuestBookingException {
        for (int i = 0; i < rooms.length; i++) {
            Guest guest1 = rooms[i].getCurrentGuest();
            if (guest1 == null) continue;

            for (int j = i + 1; j < rooms.length; j++) {
                Guest guest2 = rooms[j].getCurrentGuest();
                if (guest2 == null) continue;

                if (guest1.getGuestID().equals(guest2.getGuestID())) {
                    throw new DuplicateGuestBookingException("Duplicate booking found for Guest ID: " + guest1.getGuestID());
                }
            }
        }
        System.out.println("No duplicate guest bookings found.");
    }




}
