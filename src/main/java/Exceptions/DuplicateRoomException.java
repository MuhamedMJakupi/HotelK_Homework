package Exceptions;

public class DuplicateRoomException extends Exception {
    public DuplicateRoomException() {
        super("Duplicate room detected!");
    }
    public DuplicateRoomException(String message) {
        super(message);
    }
}
