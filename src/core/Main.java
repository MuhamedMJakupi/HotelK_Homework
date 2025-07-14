package core;

import Exceptions.DuplicateRoomException;
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
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        Hotel hotel = new Hotel("Grand Palace");

        // Add Rooms
        Room r1 = new Room(RoomType.STANDARD, new BigDecimal("50.00"),"101");
        Room r2 = new Room(RoomType.DELUXE, new BigDecimal("80.00"),"102");
        hotel.addRoom(r1);
        hotel.addRoom(r2);

        // Register Guests
        Guest g1 = new Guest( "Alice", "Brown", "alice@example.com");
        Guest g2 = new Guest( "Bob", "Smith", "bob@example.com");

        try {
            // Make Bookings
            Booking b1 = new Booking(r1, g1, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12));
            hotel.makeBooking(b1);
            System.out.println();

            // Attempt overlapping booking (should fail)
            Booking b2 = new Booking(r1, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
            hotel.makeBooking(b2);
            System.out.println();

            // Valid booking
            Booking b3 = new Booking(r2, g2, LocalDate.of(2025, 7, 11), LocalDate.of(2025, 7, 13));
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
        hotel.addService(new RoomService("core.Room Cleaning", new BigDecimal("10.00"),new BigDecimal("5.00")));
        hotel.addService(new SpaTreatment("Relaxing Massage", new BigDecimal("30.00"),60));
        hotel.addService(new LaundryService("Clothes Wash", new BigDecimal("15.00"),3));

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
        User user = new User("John", "Doe", "Manager");
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
        LaundryService laundry = new LaundryService("Wash", new BigDecimal("20"), 2);
        double[] weights = {5.5, 8.0, 7.0};
        laundry.checkWeightLimit(weights);

        //12.
        RoomService roomService = new RoomService("Cleaning", new BigDecimal("15"), new BigDecimal("5"));
        char[] steps = {'-', 'P', 'C'};
        roomService.completeSteps(steps);
        System.out.println("Steps after completion: " + Arrays.toString(steps));

        //19.
        double[] costs = {100.0, 200.0, 300.0};
        char[] tiers = {'A', 'B', 'Z'};
        HotelService.applyTierDiscounts(costs, tiers);














    }
}

