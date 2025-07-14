package core;

import Exceptions.InvalidBookingDatesException;
import Exceptions.RoomUnavailableException;
import Service.LaundryService;
import Service.RoomService;
import Service.SpaTreatment;
import Staff.FrontDeskStaff;
import Staff.HouseKeepingStaff;
import Staff.Manager;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    }
}

