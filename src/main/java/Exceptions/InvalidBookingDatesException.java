package Exceptions;

public class InvalidBookingDatesException extends Exception {
    public InvalidBookingDatesException() {
        super("Check-out date must be after check-in date.");
    }

    public InvalidBookingDatesException(String message) {
        super(message);
    }
}
