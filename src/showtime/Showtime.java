package showtime;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import show.Show;
import theater.Theater;
import trash.SeatConflictException;

public class Showtime {
    private Theater theater;
    private Show show;
    private LocalTime timeStart;

    private Set<String> reservedSeatIDs;

    public Showtime(Theater theater, Show show, LocalTime timeStart) {
        this.theater = theater;
        this.show = show;
        this.timeStart = timeStart;
        this.reservedSeatIDs = new HashSet<>();
    }

    public boolean isSeatReserved(String seatID) {
        return this.reservedSeatIDs.contains(seatID);
    }

    public void createSeatReservation(String seatID) throws SeatConflictException {
        if (isSeatReserved(seatID)) {
            throw new SeatConflictException(String.format("Failed to reserve reservation: Seat '%s' is already reserved.", seatID));
        }
        this.reservedSeatIDs.add(seatID);
    }

    public void deleteSeatReservation(String seatID) throws SeatConflictException {
        if (!isSeatReserved(seatID)) {
            throw new SeatConflictException(String.format("Failed to delete reservation: Seat '%s' is not reserved.", seatID));
        }
        this.reservedSeatIDs.remove(seatID);
    }
}
