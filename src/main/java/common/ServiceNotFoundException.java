package common;

public class ServiceNotFoundException extends Exception {
    // Default constructor
    public ServiceNotFoundException() {
        super("Service not found.");
    }

    // Constructor that allows a custom message
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
