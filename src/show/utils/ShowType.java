package show.utils;

public enum ShowType {
    ;

    private final String displayName;

    private ShowType(String displayName) { this.displayName = displayName; }

    @Override
    public String toString() { return this.displayName; }
}
