package show;

import java.util.Set;
import show.utils.ExperienceType;

public class AnimatedMovie extends Movie {
    private String studio;

    public AnimatedMovie(String title, Set<ExperienceType> types, String description, int year, String studio) {
        super(title, types, description, year);
        this.studio = studio;
    }

    public String getStudio() { return studio; }
}