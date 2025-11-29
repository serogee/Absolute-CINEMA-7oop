package show;

import java.time.Duration;
import java.util.ArrayList;

public class AnimatedMovie extends Show {
    private String studio;

    public AnimatedMovie(String title, String description, int year, Duration duration, String studio) {
        super(title, description, year, duration);
        this.studio = studio;
    }

    public String getStudio() { return studio; }

    @Override
    public String getShowTypeAsString() {
        return "Animated Movie";
    }
    
    @Override
    public ArrayList<String> getLongInfo() {
        java.util.ArrayList<String> info = new java.util.ArrayList<>();
        info.add(this.getShowTypeAsString() + "Title: " + this.toString());
        info.add("Description: " + this.getDescription());
        info.add("Animation Studio: " + this.getStudio());
        info.add(String.format("Duration: %d:%d", this.getDuration().toHours(), this.getDuration().toMinutesPart()));
        return info;
    }
}