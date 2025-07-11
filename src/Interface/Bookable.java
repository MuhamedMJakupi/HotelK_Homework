package Interface;

import java.time.LocalDate;

public interface Bookable {
    //boolean isAvailable(LocalDate checkIn, LocalDate checkOut);
    boolean isAvailable(LocalDate checkIn, LocalDate checkOut  );
    void markAsBooked();
}



