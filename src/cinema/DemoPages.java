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

        shows.add(new Movie(
            "Jurassic World: Rebirth",
            "A team travels to a former island research where three specific gigantic species of dinosaurs reside, with the goal of extracting samples that are vital for a heart disease treatment.",
            2025,
            "Scarlett Johansson, Mahershala Ali, Jonathan Bailey",
            Duration.ofMinutes(133)  
        ));

        shows.add(new Movie(
            "Damsel",
            "A young woman who accepts a marriage proposal only to realize that she is being used to repay a royal family's ancient debts and must now escape while surviving attacks from the dragon lurking in the chasm.",
            2024,
            "Millie Bobby Brown",
            Duration.ofMinutes(110)
        ));

        shows.add(new Movie(
            "Nowhere",
            "A tense and thrilling survival drama that follows a woman trapped in a shipping container, fighting for her life as she tries to escape.",
            2023,
            "Anna Castillo, Tamar Novas",
            Duration.ofMinutes(109)
        ));

        shows.add(new Movie(
            "Oppenheimer",
            "The story of J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II.",
            2023,
            "Cillian Murphy, Emily Blunt, Robert Downey Jr.",
            Duration.ofMinutes(180)
        ));
        
        
        // Animated Movies

        shows.add(new Movie(
            "Spirited Away",
            "During her family's move to the suburbs, a sullen ten-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",
            2001,
            "Rumi Hiiragi, Miyu Irino",
            Duration.ofMinutes(125)
        ));

        shows.add(new Movie(
            "Grave of the Fireflies",
            "Set in the city of Kobe during World War II, the film tells the story of siblings and war orphans Seita and Setsuko as they struggle to survive in the aftermath of the firebombing of their hometown.",
            1988,
            "Tsutomu Tatsumi, Ayano Shiraishi",
            Duration.ofMinutes(89)
        ));

        shows.add(new Movie(
            "KPop Demon Hunters",
            "A K-pop girl group, Huntrix, who lead double lives as demon hunters; they face off against a rival boy band, the Saja Boys, whose members are secretly demons.",
            2025,
            "Arden Cho, Ahn Hyo-seop, May Hong, Ji-young Yoo, Yunjin Kim, Daniel Dae Kim, Ken Jeong, Lee Byung-hun",
            Duration.ofMinutes(95)
        ));

        shows.add(new Movie(
            "Holws Moving Castle",
            "It tells the story of Sophie, a young milliner who is turned into an elderly woman by a witch who enters her shop and curses her.",
            2004,
            "Chieko Baisho, Takuya Kimura",
            Duration.ofMinutes(119)
        ));

        shows.add(new Movie(
            "How to Train Your Dragon",
            "Hiccup, an undersized teen outcast and son of the village chieftain, wishing to become a dragon slayer like the other vikings, injures a rare Night Fury dragon and instead befriends it.",
            2010,
            "Jay Baruchel, Gerard Butler, Craig Ferguson",
            Duration.ofMinutes(98
        ));
        

        // Concert Films

        shows.add(new Movie(
            "Taylor Swift: The Eras Tour",
            "A concert film documenting Taylor Swift's sixth headlining concert tour, The Eras Tour, which celebrates all of her musical eras.",
            2023,
            "Taylor Swift",
            Duration.ofMinutes(169)
        ));

        shows.add(new Movie(
            "Ariana Grande: Excuse Me, I Love You",
            "A concert film featuring performances from Ariana Grande's Sweetener World Tour, showcasing her vocal prowess and stage presence.",
            2020,
            "Ariana Grande",
            Duration.ofMinutes(97)
        ));
        
        Shows.add(new Movie(
            "A Night at the Symphony: Hollywood Bowl",
            "A concert film from Icelandic singer-songwriter Laufey's Bewitched Tour August 7 show at the Hollywood Bowl featuring performances by the Los Angeles Philharmonic, showcasing music from popular films.",
            2024,
            "Laufey",
            Duration.ofMinutes(100)
        ));


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
