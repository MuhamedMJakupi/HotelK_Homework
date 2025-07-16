
package Staff;

import Exceptions.InvalidBookingDatesException;
import core.Booking;
import core.Guest;
import core.Room;

import java.time.LocalDate;
import java.util.List;

public class Manager extends Staff {

    public Manager(String staffID, String name) {
        super(staffID, name, "Management");
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is overseeing hotel operations.");
    }

    public void approveBudget() {
        System.out.println(getName() + " is approving the budget.");
    }

    @Override
    public String toString() {
        return "Manager: " + super.toString();
    }

    //6. -Homework 3
    public Room findFirstUnbookedRoom(Room[] rooms) {
        for (Room room : rooms) {
            if (room.isAvailable())
                return room;
        }
        System.out.println("All rooms are booked.");
        return null;
    }

    //16. -Homework 3
    public void printBookedUsers(Room[] rooms) {
        for (Room room : rooms) {
            Guest guest = room.getCurrentGuest();
            if (!room.isAvailable() && guest != null) {
                System.out.println("Room ID: " + room.getRoomID() + " is booked by " + guest.getFullName());
            }
        }
    }


    //18. -Homework 3
    public void validateDates(String[] ins, String[] outs) throws InvalidBookingDatesException {
        for (int i = 0; i < ins.length; i++) {
            LocalDate in = LocalDate.parse(ins[i]);
            LocalDate out = LocalDate.parse(outs[i]);
            if (!out.isAfter(in))
                throw new InvalidBookingDatesException("Invalid at index " + i);
        }
    }

    //Bonus : 1. -Homework 4
    public static Room getEarliestUnbookedRoom(LocalDate startDate, List<Room> rooms) {
        for (Room room : rooms) {
            if (room.isAvailable(startDate, startDate.plusDays(1))) {
                return room;
            }
        }
        return null;
    }




}
