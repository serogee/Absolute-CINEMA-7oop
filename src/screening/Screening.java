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
    private ExperienceType experienceType;

    private Set<String> reservedSeatIDs;

    public Screening(Theater theater, Show show, ExperienceType experienceType) {
        this.theater = theater;
        this.show = show;
        this.experienceType = experienceType;
        this.reservedSeatIDs = new HashSet<>();
    }

    // Accessors

    public Set<String> getReservedSeatIDs() { return Collections.unmodifiableSet(this.reservedSeatIDs); }
    public Theater getTheater() { return this.theater; }
    public Show getShow() { return this.show; }
    public ExperienceType getExperienceType() { return this.experienceType; }

    // Mutators

    public void setTheater(Theater theater) { this.theater = theater; }
    public void setShow(Show show) { this.show = show; }
    public void setExperienceType(ExperienceType experienceType) { this.experienceType = experienceType; }

    // Other Methods

    /**
     * Checks if a given seat is reserved for this screening.
     * 
     * @param seatID the ID of the seat to check (e.g. "A1", "B2", etc.)
     * @return true if the seat is reserved, false otherwise
     */
    public boolean isSeatReserved(String seatID) {
        return this.reservedSeatIDs.contains(seatID);
    }

    /**
     * Creates a new seat reservation for the given row and column.
     * 
     * @param row the row number of the seat to reserve (1-indexed)
     * @param column the column letter of the seat to reserve (e.g. 'A', 'B', etc.)
     * @throws SeatConflictException if the seat is already reserved for this screening
     * @throws InvalidSeatException if the seat is not a valid seat in the theater
     */
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

    /**
     * Deletes a seat reservation for the given row and column.
     * 
     * @param row the row number of the seat to delete the reservation for (1-indexed)
     * @param column the column letter of the seat to delete the reservation for (e.g. 'A', 'B', etc.)
     * @throws SeatConflictException if the seat is not reserved for this screening
     * @throws InvalidSeatException if the seat is not a valid seat in the theater
     */
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
