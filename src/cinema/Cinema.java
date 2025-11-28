package cinema;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import cinema.utils.PageResult;
import cinema.utils.PageType;
import show.Show;
import screening.Screening;
import theater.Theater;

public class Cinema {
    // Navigation History
    private Deque<PageType> history;

    // Data Models
    private List<Show> shows;
    private List<Screening> screenings;
    private List<Theater> theathers;

    // Page Controllers
    private MainMenuPages mainMenuPages;
    private MainScreeningPages mainScreeningPages;
    private MainShowPages mainShowPages;
    private MainTheaterPages mainTheaterPages;

    public Cinema() {
        // Initialize State
        this.history = new ArrayDeque<>();
        this.shows = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.theathers = new ArrayList<>();

        // Initialize Controllers
        this.mainMenuPages = new MainMenuPages();
        // Pass 'this' so controllers can access the lists above
        this.mainScreeningPages = new MainScreeningPages(this);
        this.mainShowPages = new MainShowPages(this);
        this.mainTheaterPages = new MainTheaterPages(this);
    }

    // Accessors for Controllers
    public List<Show> getShows() { return shows; }
    public List<Screening> getScreenings() { return screenings; }
    public List<Theater> getTheathers() { return theathers; }

    /**
     * The Main Application Loop
     */
    public void start() {
        PageResult result;
        
        while (true) {
            // 1. Determine which page to display based on history
            if (this.history.isEmpty()) {
                result = this.mainMenuPages.mainPage();
            } else {
                switch (this.history.peek()) {
                    case PageType.MAIN_SHOW_EDITOR:
                        result = this.mainShowPages.mainPage();
                        break;
                    
                    // Adding Flows
                    case PageType.ADD_SHOW:
                    case PageType.SELECT_SHOW_TYPE:
                        result = this.mainShowPages.selectShowTypePage();
                        break;
                    case PageType.ADD_SHOW_MOVIE:
                        result = this.mainShowPages.addMoviePage();
                        break;
                    case PageType.ADD_SHOW_ANIMATED_MOVIE:
                        result = this.mainShowPages.addAnimatedMoviePage();
                        break;
                    case PageType.ADD_SHOW_CONCERT_FILM:
                        result = this.mainShowPages.addConcertFilmPage();
                        break;

                    // Managing Flows (Polymorphic details)
                    case PageType.MANAGE_SHOW_MOVIE:
                        result = this.mainShowPages.manageMoviePage();
                        break;
                    case PageType.MANAGE_SHOW_ANIMATED_MOVIE:
                        result = this.mainShowPages.manageAnimatedMoviePage();
                        break;
                    case PageType.MANAGE_SHOW_CONCERT_FILM:
                        result = this.mainShowPages.manageConcertFilmPage();
                        break;

                    case PageType.DELETE_SHOW:
                        result = this.mainShowPages.deleteShowPage();
                        break;
                    
                    case PageType.MAIN_MANAGE_SCREENINGS:
                        result = this.mainScreeningPages.mainPage();
                        break;
                    case PageType.ADD_SCREENING:
                        result = this.mainScreeningPages.addScreeningPage();
                        break;
                    case PageType.MANAGE_SCREENING:
                        // Handles Layout, Add Reservation, Delete Reservation
                        result = this.mainScreeningPages.manageScreeningPage();
                        break;
                    case PageType.DELETE_SCREENING:
                        // Assuming you might add this later, reusing main page for now or generic return
                        result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
                        break;

                    // ========================================================
                    // THEATER ROUTING
                    // ========================================================
                    case PageType.MAIN_THEATER_EDITOR:
                        result = this.mainTheaterPages.mainPage();
                        break;
                    case PageType.ADD_THEATER:
                        result = this.mainTheaterPages.addTheaterPage();
                        break;
                    case PageType.MANAGE_THEATER:
                        result = this.mainTheaterPages.manageTheaterPage();
                        break;
                    case PageType.DELETE_THEATER:
                        result = this.mainTheaterPages.deleteTheaterPage();
                        break;
                
                    // ========================================================
                    // FALLBACK
                    // ========================================================
                    default:
                        System.out.println("Error: PageType not implemented in Cinema.java switch.");
                        result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_START);
                        break;
                }
            }

            // 2. Handle the Result (Navigation)
            if (result == null) {
                // Should not happen if pages are well-behaved, but prevents infinite null loops
                continue; 
            }

            switch (result.getDirection()) {
                case PageResult.Navigation.NEXT:
                    this.history.push(result.getNextPage());
                    break;
                    
                case PageResult.Navigation.BACK_TO_PREVIOUS:
                    if (!this.history.isEmpty()) {
                        this.history.pop();
                    }
                    break;
                    
                case PageResult.Navigation.BACK_TO_MAIN:
                    // Clear stack until only the Main Menu remains
                    if (this.history.size() > 0) {
                        this.history.clear();
                        // Main menu is represented by empty stack in this logic, 
                        // or we can just clear it.
                    }
                    break;
                    
                case PageResult.Navigation.BACK_TO_START:
                    this.history.clear();
                    break;
                    
                case PageResult.Navigation.BACK_TO_EXIT:
                    return; // Breaks the while(true) loop and ends program
                    
                default:
                    break;
            }
        }
    }
}