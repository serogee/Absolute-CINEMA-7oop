package show;

import java.util.Set;

import show.utils.ExperienceType;

public abstract class Show {
    public final Set<ExperienceType> experiencetype;
    private String title;
    private String description; 
    private int releaseYear;

    public Show(String title, Set<ExperienceType> experiencetypes, String description, int releaseYear) {
        this.title = title;
        this.experiencetype = experiencetypes;
        this.description = description;
        this.releaseYear = releaseYear;
    }

    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public int getReleaseYear() { return this.releaseYear; }
}
