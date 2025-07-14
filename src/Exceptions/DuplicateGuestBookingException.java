package Exceptions;

public class DuplicateGuestBookingException extends Exception {
    public DuplicateGuestBookingException() {
        super("Duplicate guest booking detected.");
    }

    public DuplicateGuestBookingException(String message) {
        super(message);
    }
}
