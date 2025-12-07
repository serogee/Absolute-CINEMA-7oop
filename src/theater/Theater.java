package theater;

import java.util.Collections;
import java.util.Set;

import seat.Seat;
import screening.Screening;

public class Theater {
    private String name;
    private int nRows, nColumns;
    private Seat[][] seatLayout;
    private String seatAvailableIcon, seatUnavailableIcon;
    private Screening currentScreening;

    public Theater(String name, int nRows, int nColumns) {
        this(name, nRows, nColumns, "o", "x");
    }

    public Theater(String name, int nRows, int nColumns, String seatAvailableIcon, String seatUnavailableIcon) {
        this.name = name;
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.seatAvailableIcon = seatAvailableIcon;
        this.seatUnavailableIcon = seatUnavailableIcon;
        this.currentScreening = null;
        this.generateSeatLayout();
    }

    // Accessors

    public String getName() { return this.name; }
    public int getRowLength() { return this.nRows; }
    public int getColumnLength() { return this.nColumns; }
    
    /**
     * Retrieves a seat from the theater's seat layout based on the given row and column.
     * 
     * @param row the row number of the seat (1-indexed)
     * @param column the column letter of the seat (e.g. 'A', 'B', etc.)
     * @return the corresponding Seat object
     */
    public Seat getSeat(int row, char column) { 
        int iRow = row - 1;
        int iColumn = (int) column - 65;
        return this.seatLayout[iRow][iColumn]; 
    }

    /**
     * Retrieves the current screening for this theater.
     * 
     * @return the current screening if the theater is currently screening a show, or null if the theater is not currently screening a show.
     */
    public Screening getCurrentScreening() {
        return this.currentScreening;
    }

    // Mutators

    public void setName(String name) { this.name = name; }

    /**
     * Sets the icons used to represent available and unavailable seats in the theater's seat layout.
     * 
     * @param seatAvailableIcon the icon to use for available seats
     * @param seatUnavailableIcon the icon to use for unavailable seats
     */
    public void setSeatAvailabilityIcons(String seatAvailableIcon, String seatUnavailableIcon) {
        this.seatAvailableIcon = seatAvailableIcon;
        this.seatUnavailableIcon = seatUnavailableIcon;
    }
    
    /**
     * Generates the seat layout for the theater based on the current row and column counts.
     * This method populates the seatLayout array with Seat objects, each with a unique ID
     * based on the row and column number in the format "A1", "B2", etc.
     */
    private void generateSeatLayout() {
        this.seatLayout = new Seat[this.nRows][this.nColumns];
        for (int iRow = 0; iRow < this.nRows; iRow++) {
            for (int iColumn = 0; iColumn < this.nColumns; iColumn++) {
                this.seatLayout[iRow][iColumn] = new Seat(String.format("%c%d", (char) iColumn + 65, iRow + 1));
            }
        }
    }

    /**
     * Sets the dimensions of the theater's seat layout.
     * This method populates the seatLayout array with Seat objects, each with a unique ID
     * based on the row and column number in the format "A1", "B2", etc.
     * 
     * @param nRows the number of rows in the theater's seat layout
     * @param nColumns the number of columns in the theater's seat layout
     */
    public void setSeatLayoutDimensions(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.generateSeatLayout();
    }

    // Other Methods

    /**
     * Checks if a given seat is valid in the theater's seat layout.
     * 
     * @param row the row number of the seat (1-indexed)
     * @param column the column letter of the seat (e.g. 'A', 'B', etc.)
     * @return true if the seat is valid, false otherwise
     */
    public boolean isValidSeat(int row, char column) {
        int iRow = row - 1;
        int iColumn = (int) column - 65;
        return (iRow >= 0 && iRow < this.nRows && iColumn >= 0 && iColumn < this.nColumns);
    }

    /**
     * Generates a string representation of the theater's seat layout, including the current state of reserved seats.
     * If the theater is currently screening a show, the reserved seats for that show will be highlighted.
     * If the theater is not currently screening a show, all seats will be shown as available.
     * @return a string representation of the theater's seat layout, including the current state of reserved seats
     */
    public String generateSeatLayoutDisplay() {
        if (this.currentScreening == null) {
            return this.generateSeatLayoutDisplay(Collections.emptySet());
        }
        return this.generateSeatLayoutDisplay(this.currentScreening.getReservedSeatIDs());
    };

    /**
     * Generates a string representation of the theater's seat layout, including the current state of reserved seats.
     * If the theater is currently screening a show, the reserved seats for that show will be highlighted.
     * If the theater is not currently screening a show, all seats will be shown as available.
     * @param reservedSeatIDs a set of seat IDs that are currently reserved
     * @return a string representation of the theater's seat layout, including the current state of reserved seats
     */
    public String generateSeatLayoutDisplay(Set<String> reservedSeatIDs) {
        StringBuilder result = new StringBuilder("  ");

        for (int i = 0; i < this.nColumns; i++) {
            result.append(' ');
            result.append((char) (i + 65));
        }

        for (int iRow = 0; iRow < this.nRows; iRow++) {
            result.append(String.format("\n%2s", iRow + 1));
            for (int iColumn = 0; iColumn < this.nColumns; iColumn++) {
                result.append(' ');
                if (reservedSeatIDs.contains(this.seatLayout[iRow][iColumn].getSeatID())) {
                    result.append(this.seatUnavailableIcon);
                } else {
                    result.append(this.seatAvailableIcon);
                }
            };
        }

        return result.toString();
    };

    /**
     * Starts a screening for the given show in the theater.
     * 
     * @param screening the show to start screening
     */
    public void startScreening(Screening screening) {
        this.currentScreening = screening;
    }

    /**
     * Ends the current screening for the theater.
     * If the theater is currently screening a show, this method will clear all reserved seats for that show and set the current screening to null.
     */
    public void endScreening() {
        this.currentScreening.clearSeatReservations();
        this.currentScreening = null;
    }
}
