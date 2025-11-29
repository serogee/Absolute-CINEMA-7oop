package show;

import java.time.Duration;
import java.util.ArrayList;

public class Movie extends Show {
    public Movie(String title, String description, int releaseYear, Duration duration) {
        super(title, description, releaseYear, duration);
    }

    @Override
    public String getShowTypeAsString() {
        return "Standard Movie";
    }

    @Override
    public ArrayList<String> getLongInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add(this.getShowTypeAsString() + "Title: " + this.toString());
        info.add("Description: " + this.getDescription());
        info.add(String.format("Duration: %d:%d", this.getDuration().toHours(), this.getDuration().toMinutesPart()));
        return info;
    }
}
