package Staff;

public abstract class Staff {
    private final String staffID;
    private final String name;
    private final String department;

    public Staff(String staffID, String name, String department) {
        this.staffID = staffID;
        this.name = name;
        this.department = department;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public abstract void performDuties();

    @Override
    public String toString() {
        return name + " from " + department + " with ID " + staffID;
    }
}
