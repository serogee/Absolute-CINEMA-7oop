package show;

import java.util.Set;

import show.utils.ExperienceType;

public class Movie extends Show {
    Movie(String title, Set<ExperienceType> experiencetypes, String description, int releaseYear) {
        super(title, experiencetypes, description, releaseYear);
    }
}
