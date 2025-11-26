package cinema;

import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;

import java.util.InputMismatchException;

import cinema.utils.Option;

class MainMenuPages {

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Main Menu");
        page.addOption(new Option(PageType.MAIN_SHOW_EDITOR, "Manage Shows"));
        page.addOption(new Option(PageType.MAIN_MANAGE_SCREENINGS, "Manage Screenings"));
        page.addOption(new Option(PageType.MAIN_THEATER_EDITOR, "Manage Theaters"));

        PageResult result = null;
        while (result == null) {
            try {
                page.display();
                result = page.nextOptionResult("Input Option");
                break;
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            };
        }
        
        return result;
    }

    public void exitPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Exit");
        page.setBody("Exiting the application. Goodbye!");
        page.display();
        
    }
}
