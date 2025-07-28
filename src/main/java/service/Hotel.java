package service;

import common.*;
import common.interfaces.Bookable;
import common.interfaces.Chargeable;
import domain.Booking;
import domain.Guest;
import domain.Room;
import domain.User;
import domain.staff.Staff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class Hotel {
    private final String name;
    // Part1: 1.1 -Homework 4
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<HotelService> services = new ArrayList<>();
    private final List<Staff> staffMembers = new ArrayList<>();
    private final List<Chargeable> items = new ArrayList<>();
    // Part1 : 2.1 -Homework 4
    private Set<Guest> registeredGuests = new HashSet<>();
    // Part1 : 3.1 - Homework 4
    private Map<String, Booking> bookingsById = new HashMap<>();

    public List<HotelService> getServices() {
        return services;
    }

    public Hotel(String name) {
        this.name = name;
    }

    // changed to other one, will check again
//    public void addRoom(Room room) {
//        rooms.add(room);
//        items.add(room);
//    }

    // added for duplicate method will check again,no need for now

     public void addRoom(Room room) {
    try {
        checkForDuplicateRoom(room);
        rooms.add(room);
        items.add(room);
    } catch (DuplicateRoomException e) {
        System.out.println("Error: " + e.getMessage());
    }
}



    public void addService(HotelService service) {
        services.add(service);
        items.add(service);
    }

    public void addStaff(Staff staff) {
        staffMembers.add(staff);
    }


    // including exceptions both
    //Bonus : 3. -Homework 4
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
        booking.getRoom().setCurrentGuest(booking.getGuest());

        System.out.println("core.Booking successful: " + booking);

        //Part 1 : 3.2 -Homework 4
        bookingsById.put(booking.getBookingID(), booking);

    }

    //Part 1 : 3.3 -Homework 4
    public Booking getBookingById(String bookingId) {
        Booking booking = bookingsById.get(bookingId);
        if (booking == null) {
            System.out.println("No booking found with ID: " + bookingId);
        }
        return booking;
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


    // Part 2: 4. -Homework 4
//    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
//        List<Room> available = new ArrayList<>();
//        for (Room room : rooms) {
//            boolean isFree = true;
//            for (Booking booking : bookings) {
//                if (booking.getRoom().equals(room) &&
//                        !(checkOut.isBefore(booking.getCheckIn()) || checkIn.isAfter(booking.getCheckOut()))) {
//                    isFree = false;
//                    break;
//                }
//            }
//            if (isFree) {
//                available.add(room);
//            }
//        }
//        return available;
//    }

    // Part 3: 3. -Homework 5
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return rooms.stream()
                .filter(room -> room.isAvailable(checkIn, checkOut))
                .toList();
    }



    // Part1: 1.2 -Homework 4
//    public List<Room> getAllAvailableRooms() {
//        List<Room> available = new ArrayList<>();
//        for (Room r : rooms) {
//            if (r.isAvailable()) {
//                available.add(r);
//            }
//        }
//        return available;
//    }

    // Part1 : 1. -Homework 5
    public List<Room> getAllAvailableRooms() {
        return rooms.stream()
                .filter(Room::isAvailable)
                .toList();
    }


    // Part1 : 1.3 -Homework 4
    public List<Room> getRoomsByType(String roomType) {
        List<Room> matching = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getType().name().equalsIgnoreCase(roomType)) {
                matching.add(r);
            }
        }
        return matching;
    }

    // Part1 : 2.2 -Homework 4
    public void registerGuest(Guest guest) {
        if (registeredGuests.add(guest)) {
            System.out.println("Registered new guest: " + guest.getFullName());
        } else {
            System.out.println("Guest already registered: " + guest.getFullName());
        }
    }
    // Part1 : 2.3 -Homework 4
    public int getTotalNumberOfGuests() {
        return registeredGuests.size();
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
    public void checkRoomPricing(domain.Room[] rooms) {
        for (domain.Room r : rooms) {
            if (r.getNightlyRate().compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid rate for room: " + r.getRoomNumber());
            }
        }
    }

    //will check again needs getID(), had a getGuestId()
    //5. -Homework 3
    public void printUserBookings(domain.User user) {
        for (domain.Booking b : bookings) {
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

    // Part 2: 1. -Homework 4
    public List<Room> getRooms() {
        return rooms;
    }

    // Part 2: 2. -Homework 4
//    public List<String> getAllGuestNames() {
//        List<String> names = new ArrayList<>();
//        for (Guest guest : registeredGuests) {
//            names.add(guest.getFullName());
//        }
//        return names;
//    }

    // Part 1: 3. -Homework 5
    public List<String> getAllGuestNames() {
        return registeredGuests.stream()
                .map(Guest::getFullName)
                .toList();
    }


    // Part 2: 3. -Homework 4
//    public double calculateTotalRevenue() {
//        double total = 0.0;
//        for (Booking booking : bookings) {
//            total += booking.getCost().doubleValue();
//        }
//        return total;
//    }

    // Part 2: 1. -Homework 5
    public double calculateTotalRevenue() {
        return bookings.stream()
                .map(Booking::getCost)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }



    // Part 2: 5. -Homework 4
//    public Map<Guest, List<Booking>> getBookingsByGuest() {
//        Map<Guest, List<Booking>> map = new HashMap<>();
//        for (Booking booking : bookings) {
//            Guest guest = booking.getGuest();
//            map.putIfAbsent(guest, new ArrayList<>());
//            map.get(guest).add(booking);
//        }
//        return map;
//    }

    // Part 2: 4. -Homework 5
    public Map<Guest, List<Booking>> getBookingsByGuest() {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getGuest));
    }


    // Part 3: 1. -Homework 4
//    public String getMostFrequentRoomTypeBooked() {
//        Map<String, Integer> typeCount = new HashMap<>();
//        for (Booking booking : bookings) {
//            String type = booking.getRoom().getType().toString();
//            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
//        }
//
//        String mostFrequent = null;
//        int maxCount = 0;
//        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                maxCount = entry.getValue();
//                mostFrequent = entry.getKey();
//            }
//        }
//        return mostFrequent;
//    }

    // Part 3: 1. -Homework 5
    public String getMostFrequentRoomTypeBooked() {
        return bookings.stream()
                .collect(Collectors.groupingBy(b -> b.getRoom().getType(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey().toString())
                .orElse("None");
    }



    // Part 3: 2. -Homework 4
//    public Set<Guest> getGuestsWithMultipleBookings() {
//        Set<Guest> result = new HashSet<>();
//        Map<Guest, List<Booking>> map = getBookingsByGuest();
//
//        for (Map.Entry<Guest, List<Booking>> entry : map.entrySet()) {
//            if (entry.getValue().size() > 1) {
//                result.add(entry.getKey());
//            }
//        }
//        return result;
//    }

    //Part 3: 2. -Homework 5
    public Set<Guest> getGuestsWithMultipleBookings() {
        return bookings.stream()
                .collect(Collectors.groupingBy(Booking::getGuest, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    // Part 3 : 4.3 -Homework 4
//    public Map<Staff, Integer> getStaffTaskCounts() {
//        Map<Staff, Integer> taskMap = new HashMap<>();
//        for (Staff s : staffMembers) {
//            taskMap.put(s, s.getTasksCompleted());
//        }
//        return taskMap;
//    }

    // Part 3: 4. -Homework 5
    public Map<Staff, Integer> getStaffTaskCounts() {
        return staffMembers.stream()
                .collect(Collectors.toMap(staff -> staff, Staff::getTasksCompleted));
    }


    //Bonus : 2. -Homework 4
    public List<Room> getRoomsWithNoBookings() {
        List<Room> unbooked = new ArrayList<>();
        for (Room room : rooms) {
            boolean booked = false;
            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room)) {
                    booked = true;
                    break;
                }
            }
            if (!booked) {
                unbooked.add(room);
            }
        }
        return unbooked;
    }

    // Part1 : 2. -Homework 5
    public List<Room> getRoomsAboveRate(Double minRate) {
        return rooms.stream()
                .filter(room -> room.getNightlyRate().doubleValue() > minRate)
                .toList();
    }

    //Part 1: 4. -Homework 5
    public long countBookingsForGuest(Guest guest) {
        return bookings.stream()
                .filter(b -> b.getGuest().equals(guest))
                .count();
    }

    // Part 2: 2. -Homework 5
    public Optional<Room> getMostExpensiveRoom() {
        return rooms.stream()
                .max(Comparator.comparing(Room::getNightlyRate));
    }

    //Part 2: 3. -Homework 5
    public boolean isAnyRoomAvailable() {
        return rooms.stream()
                .anyMatch(Room::isAvailable);
    }

    // Bonus: 1. -Homework 5
    public Map<String, Double> getRevenueByRoomType() {
        return bookings.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType().toString(),
                        Collectors.summingDouble(b -> b.getCost().doubleValue())
                ));
    }

    // Bonus: 2. -Homework 5
    public List<Room> getPartiallyBookedRooms(int threshold) {
        Map<Room, Long> roomBookingNights = bookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getRoom,
                        Collectors.summingLong(b -> ChronoUnit.DAYS.between(b.getCheckIn(), b.getCheckOut()))
                ));

        return roomBookingNights.entrySet().stream()
                .filter(entry -> entry.getValue() < threshold)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
