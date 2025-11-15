package theater;

import java.util.Collections;
import java.util.Set;

import seat.Seat;
import show.utils.ExperienceType;

public abstract class Theater {
    private String name;
    private int nRows, nColumns;
    private String seatAvailableIcon, seatUnavailableIcon;
    private Seat[][] seatLayout;

    public Theater(String name, int nRows, int nColumns, String seatAvailableIcon, String seatUnavailableIcon) {
        this.name = name;
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.seatAvailableIcon = seatAvailableIcon;
        this.seatUnavailableIcon = seatUnavailableIcon;
        this.generateSeatLayout();
    }

    // Accessors

    public String getName() { return this.name; }

    public int getRowLength() { return this.nRows; }

    public int getColumnLength() { return this.nColumns; }

    public Seat getSeat(int iRow, int iColumn) { return this.seatLayout[iRow][iColumn]; }

    // Mutators

    protected String getSeatAvailableIcon() { return this.seatAvailableIcon; }

    protected String getSeatUnavailableIcon() { return this.seatUnavailableIcon; }

    protected void setSeatAvailabilityIcons(String seatAvailableIcon, String seatUnavailableIcon) {
        this.seatAvailableIcon = seatAvailableIcon;
        this.seatUnavailableIcon = seatUnavailableIcon;
    }
    
    private void generateSeatLayout() {
        this.seatLayout = new Seat[this.nRows][this.nColumns];
    }

    protected void setSeatLayoutDimensions(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.generateSeatLayout();
    }

    // Other Methods

    public void displayLayout() {
        this.displayLayout(Collections.emptySet());
    };

    public abstract void displayLayout(Set<String> reservedSeatIDs);
    
    public abstract boolean supportsExperienceType(ExperienceType experiencetype);
}
