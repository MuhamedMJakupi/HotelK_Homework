package core;

import Exceptions.InvalidBookingDatesException;
import Exceptions.RoomUnavailableException;
import Service.HotelService;
import Service.LaundryService;
import Service.RoomService;
import Service.SpaTreatment;
import Staff.FrontDeskStaff;
import Staff.HouseKeepingStaff;
import Staff.Manager;
import Staff.Staff;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;


public class Main {
    public static void main(String[] args)  {
        Hotel hotel = new Hotel("Grand Palace");

        // Add Rooms
        Room r1 = new Room(RoomType.STANDARD, new BigDecimal("50.00"),"101");
        Room r2 = new Room(RoomType.DELUXE, new BigDecimal("80.00"),"102");
        Room r3 = new Room(RoomType.SUITE, new BigDecimal("200.0"), "103");
        hotel.addRoom(r1);
        hotel.addRoom(r2);
        hotel.addRoom(r3);

        // Register Guests
        Guest g1 = new Guest( "Alice", "Brown", "alice@example.com");
        Guest g2 = new Guest( "Bob", "Smith", "bob@example.com");
        Guest g3 = new Guest("John","Doe","joh@example.com");
        //registered down at 163.

        Booking b1 = null;
        Booking b2 = null;
        Booking b3 = null;
        try {
            // Make Bookings
             b1 = new Booking(r1, g1, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12));
            hotel.makeBooking(b1);
            System.out.println();

            // Attempt overlapping booking (should fail)
            b2 = new Booking(r1, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
            hotel.makeBooking(b2);
            System.out.println();

            // Valid booking
             b3 = new Booking(r2, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
            hotel.makeBooking(b3);
            System.out.println();

            // Cancel a booking
            hotel.cancelBooking(b1.getBookingID()); // correct
            System.out.println();
        } catch (RoomUnavailableException | InvalidBookingDatesException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        //will check again, core.Hotel.java
        System.out.println("\nAvailable rooms from 2025-07-10 to 2025-07-12:");
        for (Room r : hotel.getAvailableRooms(LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12))) {
            System.out.println(r);
        }

        // Staff
        hotel.addStaff(new FrontDeskStaff("FD001", "Anna"));
        hotel.addStaff(new HouseKeepingStaff("HK001", "John"));
        hotel.addStaff(new Manager("MG001", "Elena"));

        // Services
        hotel.addService(new RoomService("Room Cleaning", new BigDecimal("10.00"),new BigDecimal("5.00"),"Room Service"));
        hotel.addService(new SpaTreatment("Relaxing Massage", new BigDecimal("30.00"),60,"SpaTreatment"));
        hotel.addService(new LaundryService("Clothes Wash", new BigDecimal("15.00"),3,"Laundry Service"));

        // Display
        System.out.println();
        hotel.showRooms();
        System.out.println();
        hotel.showBookings();
        System.out.println();
        hotel.showStaff();
        System.out.println();
        hotel.showServices();
        System.out.println();

        // Total charges
        System.out.println("Service Charges: " + hotel.calculateTotalServiceCharges());
        System.out.println("core.Booking Charges: " + hotel.calculateTotalBookingCharges());
        BigDecimal total = hotel.calculateTotalServiceCharges().add(hotel.calculateTotalBookingCharges());
        System.out.println("Total Charges (core.Booking + Services): " + total);

        System.out.println("HW3 - testing");
        //1.
        boolean[] occupancy = {false, false, false, true, false, false, false};
        System.out.println("Room vacant 3+ consecutive days? " + r1.wasVacantForThreeDays(occupancy));

        //2.
        User user = new User("John Doe", "Manager");
        char[] template = {'H', 'e', 'l', 'l', 'o', ' ', '*', '!'};
        System.out.println(user.buildGreeting(template));

        //3.
        Staff staff = new Manager("MG001", "Elena");
        int[] priorities = {5, 3, 9, 1, 7};
        System.out.println("Highest priority task index: " + staff.getHighestPriorityIndex(priorities));

        //4.
        StandardRoom stdRoom = new StandardRoom(RoomType.STANDARD, new BigDecimal("100"),"201");
        stdRoom.applyDiscounts(new char[]{'A', 'B', 'X'}); // Includes an invalid code to test default
        System.out.println("Discounted rate: " + stdRoom.getNightlyRate());


        //9.
        FrontDeskStaff frontDesk = new FrontDeskStaff("FD001", "Anna");
        String[] complaints = {"wifi", "noise", "cleanliness", "other"};
        frontDesk.handleComplaints(complaints);

        //10.
        DeluxeRoom deluxe = new DeluxeRoom(new BigDecimal("150"), 3,"202");
        int[] occupancyPerNight = {2, 3, 4};
        System.out.println("Occupancy violation? " + deluxe.checkOccupancyViolation(occupancyPerNight));

        //11.
        LaundryService laundry = new LaundryService("Wash", new BigDecimal("20"), 2,"Laundry Service");
        double[] weights = {5.5, 8.0, 7.0};
        laundry.checkWeightLimit(weights);

        //12.
        RoomService roomService = new RoomService("Cleaning", new BigDecimal("15"), new BigDecimal("5"),
                "Room Service");
        char[] steps = {'-', 'P', 'C'};
        roomService.completeSteps(steps);
        System.out.println("Steps after completion: " + Arrays.toString(steps));

        //19.
        double[] costs = {100.0, 200.0, 300.0};
        char[] tiers = {'A', 'B', 'Z'};
        HotelService.applyTierDiscounts(costs, tiers);


        System.out.println();
        System.out.println("HW4 - testing : ");

        //1.1
        System.out.println("All available rooms:");
        for (Room r : hotel.getAllAvailableRooms()) {
            System.out.println(r);
        }

        System.out.println("DELUXE rooms:");
        for (Room r : hotel.getRoomsByType("DELUXE")) {
            System.out.println(r);
        }

        //1.2
        hotel.registerGuest(g1);
        hotel.registerGuest(g2);
        hotel.registerGuest(g3);
        hotel.registerGuest(g1); // duplicate test

        System.out.println("Total unique guests: " + hotel.getTotalNumberOfGuests());

        //1.3
        System.out.println("\n--- Booking Lookup ---");
        String lookupId = b1.getBookingID(); // or any known ID
        Booking foundBooking = hotel.getBookingById(lookupId);
        if (foundBooking != null) {
            System.out.println("Found Booking: " + foundBooking);
        }


        //2.1
        System.out.println("\n--- Rooms Above Rate 60.00 ---");
        List<Room> expensiveRooms = Room.getRoomsAboveRate(hotel.getRooms(), 60.00);
        for (Room r : expensiveRooms) {
            System.out.println(r);
        }

        // Apply dynamic discount
        HotelService service = hotel.getServices().get(1); // assuming for service 2 (relaxing massage,30 - baseCost)
        service.addDiscountCode("SUMMER20", 0.20);
        System.out.println("Original cost: " + service.getBaseCost());
        service.applyDiscount("SUMMER20");


        // Find earliest available room
        Room earliest = Manager.getEarliestUnbookedRoom(LocalDate.of(2025, 7, 16), hotel.getRooms());
        System.out.println("Earliest available room: " + (earliest != null ? earliest : "None"));

        // Get rooms with no bookings
        List<Room> unbookedRooms = hotel.getRoomsWithNoBookings();
        System.out.println("Rooms with no bookings: " + unbookedRooms);


        // Part 4
        // Print all available rooms
        System.out.println("Available Rooms:");
        List<Room> availableRooms = hotel.getAllAvailableRooms();
        availableRooms.forEach(System.out::println);

        // Print rooms of a specific type
        System.out.println("\nDELUXE Rooms:");
        List<Room> deluxeRooms = hotel.getRoomsByType("DELUXE");
        deluxeRooms.forEach(System.out::println);

        // Retrieve and print a booking by its ID
        System.out.println("\nBooking by ID:");
        Booking retrievedBooking = hotel.getBookingById(b1.getBookingID());
        System.out.println(retrievedBooking);

        // Print all guest names
        System.out.println("\nAll Guest Names:");
        List<String> guestNames = hotel.getAllGuestNames();
        guestNames.forEach(System.out::println);

        // Calculate and print total revenue
        System.out.println("\nTotal Revenue from Bookings:");
        System.out.println(hotel.calculateTotalRevenue());


        //
        // Most frequently booked room type
        System.out.println("\nMost Frequently Booked Room Type:");
        String mostFrequentType = hotel.getMostFrequentRoomTypeBooked();
        System.out.println(mostFrequentType);

        // Guests with multiple bookings
        System.out.println("\nGuests with Multiple Bookings:");
        Set<Guest> frequentGuests = hotel.getGuestsWithMultipleBookings();
        frequentGuests.forEach(g -> System.out.println(g.getFullName()));


        //
        // Room occupancy simulation (if using Map<LocalDate, Boolean>)
        System.out.println("\nRoom Occupancy Check:");
        Room sampleRoom = hotel.getRooms().get(0);
        boolean available = sampleRoom.isAvailable(LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12));
        System.out.println("Is Room " + sampleRoom.getRoomID() + " available from 2025-07-10 to 2025-07-12? " + available);

        // Apply dynamic pricing (if using Map<String, Double> for discounts)
        System.out.println("\nApplying Discount to Service:");
        HotelService spa = hotel.getServices().stream()
                .filter(s -> s.getServiceName() != null && s.getServiceName().equalsIgnoreCase("Relaxing Massage"))
                .findFirst()
                .orElse(null);


        //spa.addDiscountCode("SUMMER20", 0.20);
        if (spa != null) {
            spa.applyDiscount("SUMMER20");  // Assuming "SUMMER20" exists
            System.out.println("New cost for Relaxing Massage: " + spa.getCost());
        }

        // Show staff task tracking
        System.out.println("\nStaff Task Counts:");
        Map<Staff, Integer> taskCounts = hotel.getStaffTaskCounts();
        for (Map.Entry<Staff, Integer> entry : taskCounts.entrySet()) {
            System.out.println(entry.getKey().getName() + " completed " + entry.getValue() + " tasks.");
        }


//        System.out.println("--Debug: Available services--");
//        for (HotelService s : hotel.getServices()) {
//            System.out.println("Service: " + s.getServiceName() + ", Base Cost: " + s.getBaseCost());
//        }

        //Homework 5
        System.out.println();
        System.out.println("Testing HW-5");

        System.out.println("\nAvailable Rooms:");
        hotel.getAllAvailableRooms().forEach(System.out::println);

        System.out.println("\nRooms above 60 rate:");
        hotel.getRoomsAboveRate(60.0).forEach(System.out::println);

        System.out.println("\nAll guest names:");
        hotel.getAllGuestNames().forEach(System.out::println);

        System.out.println("\nTotal booking revenue:");
        System.out.println(hotel.calculateTotalRevenue());

        System.out.println("\nMost booked room type:");
        System.out.println(hotel.getMostFrequentRoomTypeBooked());

        System.out.println("\nGuests with multiple bookings:");
        hotel.getGuestsWithMultipleBookings().forEach(System.out::println);

        System.out.println("\nStaff Task Counts:");
        hotel.getStaffTaskCounts().forEach((staf, count) ->
                System.out.println(staf.getName() + " - " + count + " tasks"));

        System.out.println("\nBookings by Guest:");
        hotel.getBookingsByGuest().forEach((guest, bookings) -> {
            System.out.println(guest.getFullName() + ":");
            bookings.forEach(System.out::println);
        });

        System.out.println("\nIs any room available?");
        System.out.println(hotel.isAnyRoomAvailable());

        System.out.println("=== Revenue by Room Type ===");
        hotel.getRevenueByRoomType().forEach((roomType, revenue) ->
                System.out.println(roomType + ": $" + revenue));

        System.out.println("=== Partially Booked Rooms (less than 3 nights) ===");
        List<Room> partials = hotel.getPartiallyBookedRooms(3);
        partials.forEach(room -> System.out.println(room));


        String url = "jdbc:mysql://localhost:3306/hotelkey";
        String man = "root";
        String password = "Mustafa_1903";

        try (Connection conn = DriverManager.getConnection(url, man, password)) {
            if (conn != null) {
                System.out.println("✅ Connected to the database!");
            } else {
                System.out.println("❌ Connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection error: " + e.getMessage());
        }

        RoomService roomServiceDB = new RoomService("Cleaning", new BigDecimal("15"), new BigDecimal("5"),
                "Room Service");

        // Get All
        List<Room> allRoomsAgain1 = roomServiceDB.getAll();
        System.out.println("All Rooms:");
        allRoomsAgain1.forEach(System.out::println);

        // Insert Room
        Room room = new Room(RoomType.DELUXE, new BigDecimal("120.00"), "505");
        roomServiceDB.insert(room);

        // Get All
        List<Room> allRoomsAgain2 = roomServiceDB.getAll();
        System.out.println("All Rooms:");
        allRoomsAgain2.forEach(System.out::println);

        // Get By ID
        Room fetched = roomServiceDB.getById(room.getRoomID());
        System.out.println("Fetched Room: " + fetched);

        // Get All
        List<Room> allRooms = roomServiceDB.getAll();
        System.out.println("All Rooms:");
        allRooms.forEach(System.out::println);

        // Update Room
        room.setNightlyRate(new BigDecimal("150.00"));
        room.setRoomNumber("Updated505");
        roomServiceDB.update(room);
        System.out.println("Room updated.");

        // Get All
        List<Room> allRoomsAgain3 = roomServiceDB.getAll();
        System.out.println("All Rooms:");
        allRoomsAgain3.forEach(System.out::println);

        // Delete Room
        roomServiceDB.deleteById(room.getRoomID());
        System.out.println("Room deleted.");

        // Get All
        List<Room> allRoomsAgain4 = roomServiceDB.getAll();
        System.out.println("All Rooms:");
        allRoomsAgain4.forEach(System.out::println);

        System.out.println("Created a branch for Homework 7.");
        System.out.println("No changes made in code, only in .sql file.");
        System.out.println("Created for purpose to know that homework 7, wasn't skipped.");

    }
}


