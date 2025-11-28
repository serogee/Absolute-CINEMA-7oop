package show;

import java.util.Set;
import show.utils.ExperienceType;

public class ConcertFilm extends Movie {
    private String artist;

    public ConcertFilm(String title, Set<ExperienceType> types, String description, int year, String artist) {
        super(title, types, description, year);
        this.artist = artist;
    }

    public String getArtist() { return artist; }
}