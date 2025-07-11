package Staff;

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
}
