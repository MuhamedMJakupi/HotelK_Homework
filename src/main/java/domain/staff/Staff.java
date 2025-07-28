package domain.staff;

public abstract class Staff {
    private final String staffID;
    private final String name;
    private final String department;

    // Part 3 : 4.1 -Homework 4
    protected int tasksCompleted = 0;


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

    //3. - Homework 3
    public int getHighestPriorityIndex(int[] tasks) {
        int minIndex = 0;
        for (int i = 1; i < tasks.length; i++) {
            if (tasks[i] < tasks[minIndex])
                minIndex = i;
        }
        return minIndex;
    }

    // Part 3: 4.2 -Homework 4
    public void completeTask() {
        tasksCompleted++;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

}
