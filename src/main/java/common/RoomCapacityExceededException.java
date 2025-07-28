package common;

public class RoomCapacityExceededException extends Exception {
    public RoomCapacityExceededException() {
        super("Room capacity has been exceeded.");
    }

    public RoomCapacityExceededException(String message) {
        super(message);
    }
}
