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

        // Initialize Main Menu
        this.mainMenuPages = new MainMenuPages();

        // Pass the Cinema instance to page controllers for access to data
        this.mainScreeningPages = new MainScreeningPages(this);
        this.mainShowPages = new MainShowPages(this);
        this.mainTheaterPages = new MainTheaterPages(this);
    }

    // Accessors for Controllers
    public List<Show> getShows() { return shows; }
    public List<Screening> getScreenings() { return screenings; }
    public List<Theater> getTheathers() { return theathers; }

    public void start() {
        PageResult result;
        
        while (true) {
            // Determine which page to display based on history
            if (this.history.isEmpty()) {
                result = this.mainMenuPages.mainPage();
            } else {
                switch (this.history.peek()) {
                    // Manage Screenings
                    case PageType.MAIN_MANAGE_SCREENINGS:
                        result = this.mainScreeningPages.mainPage();
                        break;
                    case PageType.MANAGE_SCREENING:
                        result = this.mainScreeningPages.manageScreeningPage();
                        break;
                    case PageType.SHOW_SCREENING_SEAT_LAYOUT:
                        result = this.mainScreeningPages.showSeatLayoutPage();
                        break;
                    case PageType.ADD_RESERVATION:
                        result = this.mainScreeningPages.addSeatReservationPage();
                        break;
                    case PageType.DELETE_RESERVATION:
                        result = this.mainScreeningPages.deleteSeatReservationPage();
                        break;
                    case PageType.EDIT_SCREENING_SHOW:
                        result = this.mainScreeningPages.editScreeningShowPage();
                        break;
                    case PageType.EDIT_SCREENING_THEATER:
                        result = this.mainScreeningPages.editScreeningTheaterPage();
                        break;
                    
                    case PageType.ADD_SCREENING:
                        result = this.mainScreeningPages.addScreeningPage();
                        break;
                    case PageType.DELETE_SCREENING:
                        result = this.mainScreeningPages.deleteScreeningPage();
                        break;

                    // Manage Shows
                    case PageType.MAIN_SHOW_EDITOR:
                        result = this.mainShowPages.mainPage();
                        break;
                    case PageType.ADD_SHOW:
                        result = this.mainShowPages.addShowPage();
                        break;
                    case PageType.ADD_SHOW_MOVIE:
                        result = this.mainShowPages.addShowMoviePage();
                        break;
                    case PageType.ADD_SHOW_ANIMATED_MOVIE:
                        result = this.mainShowPages.addShowAnimatedMoviePage();
                        break;
                    case PageType.ADD_SHOW_CONCERT_FILM:
                        result = this.mainShowPages.addShowConcertFilmPage();
                        break;
                    // case PageType.EDIT_SHOW_TITLE:
                    //     result = this.mainShowPages.editShowMovieTitlePage();
                    //     break;
                    // case PageType.EDIT_SHOW_DESCRIPTION:
                    //     result = this.mainShowPages.editShowMovieDescriptionPage();
                    //     break;
                    // case PageType.EDIT_SHOW_RELEASE_YEAR:
                    //     result = this.mainShowPages.editShowMovieReleaseYearPage();
                    //     break;
                    // case PageType.MANAGE_SHOW_MOVIE:
                        // result = this.mainShowPages.manageShowMoviePage();
                        // break;
                    // case PageType.MANAGE_SHOW_ANIMATED_MOVIE:
                        // result = this.mainShowPages.manageShowAnimatedMoviePage();
                        // break;
                    // case PageType.EDIT_ANIMATION_STYLE:
                        // result = this.mainShowPages.manageShowConcertFilmPage();
                        // break;
                    // case PageType.MANAGE_SHOW_CONCERT_FILM:
                        // result = this.mainShowPages.manageShowConcertFilmPage();
                        // break;
                    // case PageType.EDIT_CONCERT_PERFORMER:
                        // result = this.mainShowPages.editShowConcertFilmPerformerPage();
                        // break;
                    case PageType.DELETE_SHOW:
                        result = this.mainShowPages.deleteShowPage();
                        break;

                    // Manage Theaters
                    case PageType.MAIN_THEATER_EDITOR:
                        result = this.mainTheaterPages.mainPage();
                        break;
                    case PageType.MANAGE_THEATER:
                        result = this.mainTheaterPages.manageTheaterPage();
                        break;
                    case PageType.SET_CURRENT_SCREENING:
                        result = this.mainTheaterPages.setCurrentScreeningPage();
                        break;
                    case PageType.SHOW_THEATER_SEAT_LAYOUT:
                        result = this.mainTheaterPages.showTheaterSeatLayoutPage();
                        break;
                    case PageType.EDIT_THEATER_DIMENSIONS:
                        result = this.mainTheaterPages.editTheaterDimensionsPage();
                        break;
                    case PageType.EDIT_THEATER_NAME:
                        result = this.mainTheaterPages.editTheaterNamePage();
                        break;
                    case PageType.ADD_THEATER:
                        result = this.mainTheaterPages.addTheaterPage();
                        break;
                    case PageType.DELETE_THEATER:
                        result = this.mainTheaterPages.deleteTheaterPage();
                        break;
                
                    // Fall Back
                    default:
                        result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_START);
                        break;
                }
            }

            // Handle the Result (Navigation)
            if (result == null) {
                // Should not happen if pages are well-behaved, but prevents infinite null loops
                throw new IllegalStateException("PageResult is null, which should not happen.");
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
                    if (this.history.size() > 1) {
                        PageType mainPage = this.history.peekLast();
                        this.history.clear();
                        this.history.push(mainPage);
                    }
                    break;
                case PageResult.Navigation.BACK_TO_START:
                    this.history.clear();
                    break;
                case PageResult.Navigation.BACK_TO_EXIT:
                    return;
                default:
                    break;
            }

            // Make sure that no working instance is active 
            if (this.history.size() <= 1) {
                this.mainScreeningPages.clearWorkingScreening();
                this.mainShowPages.clearWorkingShow();
                this.mainTheaterPages.clearWorkingTheater();
            }
        }
    }
}
