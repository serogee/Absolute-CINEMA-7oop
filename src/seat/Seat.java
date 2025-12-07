package seat;

public class Seat {
    private final String seatID;

    public Seat(String seatID) {
        this.seatID = seatID;
    }

    /**
     * Returns the ID of this seat in the format "A1", "B2", etc.
     * 
     * @return the ID of this seat
     */
    public String getSeatID() {
        return this.seatID;
    }
}
