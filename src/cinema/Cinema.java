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
    private Deque<PageType> history;
    private List<Show> shows;
    private List<Screening> screenings;
    private List<Theater> theathers;
    private MainMenuPages mainMenuPages;
    private MainScreeningPages mainScreeningPages;
    private MainShowPages mainShowPages;
    private MainTheaterPages mainTheaterPages;

    public Cinema() {
        this.history = new ArrayDeque<>();
        this.shows = new ArrayList<>();
        this.screenings = new ArrayList<>();
        this.theathers = new ArrayList<>();

        this.mainMenuPages = new MainMenuPages();
        this.mainScreeningPages = new MainScreeningPages(this);
        this.mainShowPages = new MainShowPages(this);
        this.mainTheaterPages = new MainTheaterPages(this);
    }

    public List<Show> getShows() {
        return shows;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public List<Theater> getTheathers() {
        return theathers;
    }

    public void start() {
        PageResult result;
        
        while (true) {
            if (this.history.isEmpty()) {
                result = this.mainMenuPages.mainPage();
            } else {
                switch (this.history.peek()) {
                    case PageType.MAIN_SHOW_EDITOR:
                        result = this.mainShowPages.mainPage();
                        break;
                    case PageType.MAIN_MANAGE_SCREENINGS:
                        result = this.mainScreeningPages.mainPage();
                        break;
                    case PageType.ADD_SCREENING:
                        result = this.mainScreeningPages.addScreeningPage();
                        break;
                    case PageType.MAIN_THEATER_EDITOR:
                        result = this.mainTheaterPages.mainPage();
                        break;
                
                    default:
                        result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_START);
                        break;
                }
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
                    System.out.println("DEBUG: BACK_TO_MAIN triggered.");
                    if (this.history.size() > 1) {
                        System.out.println("DEBUG: BACK_TO_MAIN triggered. History size: " + this.history.size());
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
        }
    }
}
