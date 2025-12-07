package show;

import java.time.Duration;
import java.util.ArrayList;

public class ConcertFilm extends Show {
    private String artist;

    public ConcertFilm(String title, String description, int year, String artist, Duration duration) {
        super(title, description, year, duration);
        this.artist = artist;
    }

    public String getArtist() { return this.artist; }
    public void setArtist(String artistName) { this.artist = artistName; }

    /**
     * Returns a string representing the type of show.
     * 
     * @return a string representing the type of show
     */
    @Override
    public String getShowTypeAsString() {
        return "Concert Film";
    }
    
    /**
     * Returns a list of strings containing detailed information about the show.
     * The list contains the following information in the following order:
     * - The show type and title
     * - The description of the show
     * - The artist of the show
     * - The duration of the show in hours and minutes
     * 
     * @return a list of strings containing detailed information about the show
     */
    @Override
    public ArrayList<String> getLongInfo() {
        java.util.ArrayList<String> info = new java.util.ArrayList<>();
        info.add(this.getShowTypeAsString() + " Title: " + this.toString());
        info.add(" - Description: " + this.getDescription());
        info.add(" - Artist: " + this.getArtist());
        info.add(String.format(" - Duration: %d:%d", this.getDuration().toHours(), this.getDuration().toMinutesPart()));
        return info;
    }
}