package show;

import java.time.Duration;
import java.util.ArrayList;

public abstract class Show {
    private String title;
    private String description;
    private int releaseYear;
    private Duration duration;

    public Show(String title, String description, int releaseYear, Duration duration) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public int getReleaseYear() { return this.releaseYear; }
    public Duration getDuration() { return this.duration; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public void setDuration(Duration duration) { this.duration = duration; }

    @Override
    public String toString() {
        return this.title + " (" + this.releaseYear + ")";
    }
    
    public String getShortInfo() {
        return String.format("%s (%d) [%d:%d] - %s", this.title, this.releaseYear, this.duration.toHours(), this.duration.toMinutesPart(), this.getShowTypeAsString());
    }

    public abstract String getShowTypeAsString();
    public abstract ArrayList<String> getLongInfo();
}
