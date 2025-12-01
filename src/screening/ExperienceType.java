package screening;

public enum ExperienceType {
    STANDARD_2D("Standard 2D"),
    STANDARD_3D("Standard 3D"),
    IMAX("IMAX");

    private final String displayName;

    private ExperienceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
