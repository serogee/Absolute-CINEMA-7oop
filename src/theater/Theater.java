package theater;

import java.util.Collections;
import java.util.Set;

import seat.Seat;
import theater.utils.LightingMode;
import theater.utils.SoundMode;
import screening.Screening;


public class Theater {
    private String name;
    private int nRows, nColumns;
    private Seat[][] seatLayout;
    private String seatAvailableIcon, seatUnavailableIcon;
    private LightingMode lightingMode;
    private SoundMode soundMode;
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
        this.lightingMode = LightingMode.BLACKOUT;
        this.soundMode = SoundMode.STANDARD;
        this.currentScreening = null;
        this.generateSeatLayout();
    }

    // Accessors

    public String getName() { return this.name; }

    public int getRowLength() { return this.nRows; }

    public int getColumnLength() { return this.nColumns; }

    public LightingMode getLightingMode() { return this.lightingMode; }

    public SoundMode getSoundMode() { return this.soundMode; }
    
    public Seat getSeat(int row, char column) { 
        int iRow = row - 1;
        int iColumn = (int) column - 65;
        return this.seatLayout[iRow][iColumn]; 
    }

    public Screening getCurrentScreening() {
        return this.currentScreening;
    }

    // Mutators

    public void setSeatAvailabilityIcons(String seatAvailableIcon, String seatUnavailableIcon) {
        this.seatAvailableIcon = seatAvailableIcon;
        this.seatUnavailableIcon = seatUnavailableIcon;
    }
    
    private void generateSeatLayout() {
        this.seatLayout = new Seat[this.nRows][this.nColumns];
        for (int iRow = 0; iRow < this.nRows; iRow++) {
            for (int iColumn = 0; iColumn < this.nColumns; iColumn++) {
                this.seatLayout[iRow][iColumn] = new Seat(String.format("%c%d", (char) iColumn + 65, iRow + 1));
            }
        }
    }

    public void setSeatLayoutDimensions(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.generateSeatLayout();
    }

    // Other Methods

    public boolean isValidSeat(int row, char column) {
        int iRow = row - 1;
        int iColumn = (int) column - 65;
        return (iRow >= 0 && iRow < this.nRows && iColumn >= 0 && iColumn < this.nColumns);
    }

    public String generateSeatLayoutDisplay() {
        if (this.currentScreening == null) {
            return this.generateSeatLaoutDisplay(Collections.emptySet());
        }
        return this.generateSeatLaoutDisplay(this.currentScreening.getResearvedSeatIDs());
    };

    public String generateSeatLaoutDisplay(Set<String> reservedSeatIDs) {
        StringBuilder result = new StringBuilder("  ");

        for (int i = 0; i < this.nColumns; i++) {
            result.append(' ');
            result.append((char) i + 65);
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

    public void startScreening(Screening screening) {
        this.currentScreening = screening;
    }

    public void endScreening(Screening screening) {
        this.currentScreening.clearSeatReservations();
        this.currentScreening = null;
    }
}
