package Exceptions;

public class RoomUnavailableException extends Exception {
    public RoomUnavailableException() {
        super("core.Room is unavailable for the selected dates.");
    }

    public RoomUnavailableException(String message) {
        super(message);
    }
}
