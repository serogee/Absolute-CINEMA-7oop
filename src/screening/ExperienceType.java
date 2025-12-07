package screening;

public enum ExperienceType {
    STANDARD_2D("Standard 2D"),
    STANDARD_3D("Standard 3D"),
    IMAX("IMAX");

    private final String displayName;

    private ExperienceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the experience type.
     * @return the display name of the experience type
     */
    @Override
    public String toString() {
        return this.displayName;
    }
}
