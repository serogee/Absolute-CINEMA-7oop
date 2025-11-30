package cinema;

import java.time.Duration;
import java.util.List;

import cinema.utils.Option;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import show.Movie;
import show.Show;
import theater.Theater;

public class DemoPages {

    public static PageResult mainPage(Cinema cinema) {

        List<Show> shows = cinema.getShows();
        List<Theater> theaters = cinema.getTheathers();

        // Movies
        shows.add(new Movie(
            "Iron Man", 
            "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.", 
            2008, 
            "Robert Downey Jr.", 
            Duration.ofMinutes(90)
        ));

        // Animated Movies
        
        // Concert Films


        // Theaters
        theaters.add(new Theater("Theater 1", 20, 20));
        theaters.add(new Theater("Theater 2", 20, 15));
        
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Load Demo Data");
        page.setSubTitle("Demo Movies and Theaters have been loaded!");

        page.display();

        page.setEnterOption(new Option(PageResult.Navigation.BACK_TO_START, "Continue"));

        page.nextOptionResultInputLoop("Press Enter to Continue");

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_START);
    }
}
