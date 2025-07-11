package Staff;

public class FrontDeskStaff extends Staff {

    public FrontDeskStaff(String staffID, String name) {
        super(staffID, name, "Front Desk");
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is checking in guests.");
    }

    public void greet() {
        System.out.println("Hello, welcome to the hotel!");
    }

    public void greet(String guestName) {
        System.out.println("Hello, " + guestName + "! Welcome to the hotel.");
    }

    @Override
    public String toString() {
        return "FrontDeskStaff: " + super.toString();
    }
}
