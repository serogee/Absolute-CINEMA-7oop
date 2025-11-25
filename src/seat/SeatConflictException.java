package seat;

public class SeatConflictException extends Exception {
    public SeatConflictException() {
        super();
    }

    public SeatConflictException(String message) {
        super(message);
    }
}
