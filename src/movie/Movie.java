package movie;

public class Movie {
    public final MovieType movieType;
    String title;

    public Movie(MovieType movieType, String title) {
        this.movieType = movieType;
        this.title = title;
    }
}
