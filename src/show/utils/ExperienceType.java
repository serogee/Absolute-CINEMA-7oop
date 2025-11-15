package show.utils;

public enum ExperienceType {
    STANDARD_2D("Standard 2D", 0),
    STANDARD_3D("Standard 3D", 80),
    IMAX("IMAX", 150);

    private final String displayName;
    private final double priceOffset;

    private ExperienceType(String displayName, double priceOffset) {
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
