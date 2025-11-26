package cinema;

import java.util.InputMismatchException;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.Screening;
import theater.Theater;
import show.Show;

class MainScreeningPages {
    private Cinema cinema;
    private Screening workingScreening;

    MainScreeningPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingScreening = null;
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Screenings");

        for (int i = 0; i < this.cinema.getScreenings().size(); i++) {
            Screening screening = this.cinema.getScreenings().get(i);
            
            page.addDisplayOption(String.format(
                "[%d] %s {%d/%d seats}: %s (%d)", 
                i + 1, 
                screening.getTheater().getName(), 
                screening.getResearvedSeatIDs().size(), 
                screening.getTheater().getRowLength() * screening.getTheater().getColumnLength(), 
                screening.getShow().getTitle(), 
                screening.getShow().getReleaseYear()
            ));
        }
        page.addCustomOption(new CustomOption(PageType.ADD_SCREENING, "Add Screening", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SCREENING, "Delete Screening", "D"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result = null;
        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                if (intInput.getPageResult() == null) { 
                    this.workingScreening = this.cinema.getScreenings().get(intInput.getValue());
                    result = PageResult.createResultNextPage(PageType.MANAGE_SCREENING);
                } else {
                    result = intInput.getPageResult();
                }
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            } catch (IndexOutOfBoundsException e) {
                page.setErrorMessage("Please select a valid screening number!");
            }
        }
        return result;
    }

    public PageResult addScreeningPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add Screening");

        for (Theater theater : this.cinema.getTheathers()) {
            page.addDisplayOption("Theater: " + theater.getName() + " | Seats: " + (theater.getRowLength() * theater.getColumnLength()));
        }

        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_START, "Return to Main Menu", "M"));

        PageResult.Int intInput;
        PageResult result = null;

        Theater selectedTheater = null;

        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Select Theater");
                if (intInput.getPageResult() == null) {
                    selectedTheater = this.cinema.getTheathers().get(intInput.getValue());
                    result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
                } else {
                    result = intInput.getPageResult();
                }
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            } catch (IndexOutOfBoundsException e) {
                page.setErrorMessage("Please select a valid theater number!");
            };
        }

        Show selectedShow = null;

        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Select Show");
                if (intInput.getPageResult() == null) {
                    selectedShow = this.cinema.getShows().get(intInput.getValue());
                    result = PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
                } else {
                    result = intInput.getPageResult();
                }
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            }catch (IndexOutOfBoundsException e) {
                page.setErrorMessage("Please select a valid show number!");
            };
        }

        if (selectedTheater != null && selectedShow != null) {
            this.cinema.getScreenings().add(new Screening(selectedTheater, selectedShow));
        }
        
        return result;
    }
}
