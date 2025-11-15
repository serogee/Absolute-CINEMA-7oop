package seat;

public enum SeatType {
    ECONOMY("Economy", -50),
    REGULAR("Regular", 0),
    PREMIUM("Premium", 100);

    private final String displayName;
    private final double priceOffset;

    private SeatType(String displayName, double priceOffset) {
        this.displayName = displayName;
        this.priceOffset = priceOffset;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public double getPriceOffset() {
        return this.priceOffset;
    }
}