import java.util.UUID;

public class Guest {
    private final String guestID;
    private final String firstName;
    private final String lastName;
    private final String email;

    public Guest(String firstName, String lastName, String email) {
        this.guestID = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getGuestID() {
        return guestID;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Guest other = (Guest) obj;
        return guestID.equals(other.guestID);
    }

    @Override
    public int hashCode() {
        return guestID.hashCode();
    }


    @Override
    public String toString() {
        return "Guest{" + guestID + ", " + getFullName() + ", " + email + "}";
    }
}
