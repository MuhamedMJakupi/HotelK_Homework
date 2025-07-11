package Staff;

public class HouseKeepingStaff extends Staff {

    public HouseKeepingStaff(String staffID, String name) {
        super(staffID, name, "Housekeeping");
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is cleaning rooms.");
    }

    @Override
    public String toString() {
        return "HousekeepingStaff: " + super.toString();
    }
}
