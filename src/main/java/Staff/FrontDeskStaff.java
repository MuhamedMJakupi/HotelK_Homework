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

    //9. -Homework 3
    public void handleComplaints(String[] complaints) {
        for (String c : complaints) {
            switch (c.toLowerCase()) {
                case "wifi" -> System.out.println("Report sent to IT support.");
                case "cleanliness" -> System.out.println("Notified housekeeping.");
                case "noise" -> System.out.println("Security has been alerted.");
                default -> System.out.println("Complaint noted: " + c);
            }
        }
    }

}
