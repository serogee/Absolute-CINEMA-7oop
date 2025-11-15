package theater.utils;

public enum LightMode {
    BLACKOUT("Blackout"),
    DIM("Dim"),
    AMBIENT("Ambient"); 

    private final String displayName;

    private LightMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() { return this.displayName; }
}
