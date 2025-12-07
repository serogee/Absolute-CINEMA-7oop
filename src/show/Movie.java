package show;

import java.time.Duration;
import java.util.ArrayList;

public class Movie extends Show {
    public String leadActor;

    public Movie(String title, String description, int releaseYear, String leadActor, Duration duration) {
        super(title, description, releaseYear, duration);
        this.leadActor = leadActor;
    }

    public String getLeadActor() { return this.leadActor; }
    public void setLeadActor(String leadActor) { this.leadActor = leadActor; }

    /**
     * Returns the type of show as a string.
     * 
     * @return a string representing the type of show
     */
    @Override
    public String getShowTypeAsString() {
        return "Standard Movie";
    }

    /**
     * Returns a list of strings containing detailed information about the show.
     * The list contains the following information in the following order:
     * - The show type and title
     * - The description of the show
     * - The lead actor of the show
     * - The duration of the show in hours and minutes
     * 
     * @return a list of strings containing detailed information about the show
     */
    @Override
    public ArrayList<String> getLongInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add(this.getShowTypeAsString() + " Title: " + this.toString());
        info.add(" - Description: " + this.getDescription());
        info.add(" - Lead Actor: " + this.getLeadActor());
        info.add(String.format(" - Duration: %d:%d", this.getDuration().toHours(), this.getDuration().toMinutesPart()));
        return info;
    }
}
