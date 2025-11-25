package cinema;

import java.util.InputMismatchException;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import theater.Theater;

class MainTheaterPages {
    Cinema cinema;
    Theater workingTheater;

    public MainTheaterPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingTheater = null;
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Theater Management");

        for (Theater theater : this.cinema.getTheathers()) {
            page.addDisplayOption("Theater: " + theater.getName() + " | Seats: " + (theater.getRowLength() * theater.getColumnLength()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_SCREENING, "Add Theater", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SCREENING, "Delete Theater", "D"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result;
        while (true) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                if (intInput.getPageResult() == null) { 
                    this.workingTheater = this.cinema.getTheathers().get(intInput.getValue());
                    result = PageResult.createResultNextPage(PageType.MANAGE_THEATER);
                } else {
                    result = intInput.getPageResult();
                }
                break;
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            } catch (IndexOutOfBoundsException e) {
                page.setErrorMessage("Please select a valid screening number!");
            }
        }
        return result;
    }
}
