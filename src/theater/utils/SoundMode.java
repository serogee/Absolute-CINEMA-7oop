package theater.utils;

public enum SoundMode {
    STANDARD("Standard"),
    SURROUND("Surround"),
    ENHANCED_SURROUND("Enhanced Surround"),
    BASS_BOOSTED("Bass Boosted");

    private final String displayName;

    private SoundMode(String displayName) { this.displayName = displayName; }

    @Override
    public String toString() { return this.displayName; }

}
