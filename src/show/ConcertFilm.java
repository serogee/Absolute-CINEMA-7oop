package show;

import java.time.Duration;
import java.util.ArrayList;

public class ConcertFilm extends Show {
    private String artist;

    public ConcertFilm(String title, String description, int year, String artist, Duration duration) {
        super(title, description, year, duration);
        this.artist = artist;
    }

    public String getArtist() { return artist; }

    @Override
    public String getShowTypeAsString() {
        return "Concert Film";
    }
    
    @Override
    public ArrayList<String> getLongInfo() {
        java.util.ArrayList<String> info = new java.util.ArrayList<>();
        info.add(this.getShowTypeAsString() + "Title: " + this.toString());
        info.add("Description: " + this.getDescription());
        info.add("Artist: " + this.getArtist());
        info.add(String.format("Duration: %d:%d", this.getDuration().toHours(), this.getDuration().toMinutesPart()));
        return info;
    }
}