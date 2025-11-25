package theater.utils;

public enum LightingMode {
    BLACKOUT("Blackout"),
    DIM("Dim"),
    AMBIENT("Ambient"); 

    private final String displayName;

    private LightingMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() { return this.displayName; }
}
