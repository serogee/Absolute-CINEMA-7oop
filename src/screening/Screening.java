package screening;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import show.Show;
import theater.Theater;
import seat.SeatConflictException;
import seat.InvalidSeatException;


public class Screening {
    private Theater theater;
    private Show show;

    private Set<String> reservedSeatIDs;

    public Screening(Theater theater, Show show) {
        this.theater = theater;
        this.show = show;
        this.reservedSeatIDs = new HashSet<>();
    }

    // Accessors

    public Set<String> getResearvedSeatIDs() { return Collections.unmodifiableSet(this.reservedSeatIDs); }

    public Theater getTheater() { return this.theater; }

    public Show getShow() { return this.show; }

    // Mutators

    public void setTheater(Theater theater) { this.theater = theater; }

    public void setShow(Show show) { this.show = show; }

    // Other Methods

    public boolean isSeatReserved(String seatID) {
        return this.reservedSeatIDs.contains(seatID);
    }

    public void createSeatReservation(int row, char column) throws SeatConflictException, InvalidSeatException {
        String seatID = String.format("%c%d", column, row);
        if (!theater.isValidSeat(row, column)) {
            throw new InvalidSeatException(String.format("Failed to reserve reservation: Seat '%s' is not a valid seat.", seatID));
        }
        if (isSeatReserved(seatID)) {
            throw new SeatConflictException(String.format("Failed to reserve reservation: Seat '%s' is already reserved.", seatID));
        }
        this.reservedSeatIDs.add(seatID);
    }

    public void deleteSeatReservation(int row, char column) throws SeatConflictException, InvalidSeatException {
        String seatID = String.format("%c%d", column, row);
        if (!theater.isValidSeat(row, column)) {
            throw new InvalidSeatException(String.format("Failed to delete reservation: Seat '%s' is not a valid seat.", seatID));
        }
        if (!isSeatReserved(seatID)) {
            throw new SeatConflictException(String.format("Failed to delete reservation: Seat '%s' is not reserved.", seatID));
        }
        this.reservedSeatIDs.remove(seatID);
    }

    public void clearSeatReservations() { this.reservedSeatIDs.clear(); }
}
