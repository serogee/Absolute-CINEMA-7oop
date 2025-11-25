package seat;

public class InvalidSeatException extends Exception {
    public InvalidSeatException() {
        super();
    }

    public InvalidSeatException(String message) {
        super(message);
    }
}
