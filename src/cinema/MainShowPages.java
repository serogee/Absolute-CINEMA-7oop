package cinema;

import java.util.InputMismatchException;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import show.Show;

class MainShowPages {
    private Cinema cinema;
    private Show workingShow;

    MainShowPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingShow = null;
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Shows");

        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            page.addDisplayOption(String.format("[%d] %s (%d)", i + 1, this.cinema.getShows().get(i).getTitle(), this.cinema.getShows().get(i).getReleaseYear()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_SHOW, "Add Show", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SHOW, "Delete Show", "D"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result;
        while (true) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                if (intInput.getPageResult() == null) { 
                    this.workingShow = this.cinema.getShows().get(intInput.getValue());
                    result = PageResult.createResultNextPage(PageType.MANAGE_SHOW);
                } else {
                    result = intInput.getPageResult();
                }
                break;
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            } catch (IndexOutOfBoundsException e) {
                page.setErrorMessage("Please select a valid show number!");
            }
        }
        return result;
    }

    // public PageResult addShowPage() {
    //     PageBuilder page = new PageBuilder();
    //     page.setTitle("Add Show");

    //     page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
    //     page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_START, "Return to Main Menu", "M"));

        
    // }
}
