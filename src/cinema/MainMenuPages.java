package cinema;

import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;

import java.util.InputMismatchException;

import cinema.utils.CustomOption;
import cinema.utils.Option;

class MainMenuPages {

    /**
     * Displays the main menu page to the user, allowing them to choose from several options:
     *   - Manage Shows: Manage all shows in the cinema.
     *   - Manage Screenings: Manage all screenings in the cinema.
     *   - Manage Theaters: Manage all theaters in the cinema.
     *   - Load Demo Data: Load demo data into the program.
     *   - Quit: Quit the program.
     * @return The result of the user's input, which can be used to navigate to the next page.
     */
    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Main Menu");
        page.setSubTitle("Welcome to Absolute CINEMA!");
        page.addOption(new Option(PageType.MAIN_SHOW_EDITOR, "Manage Shows"));
        page.addOption(new Option(PageType.MAIN_MANAGE_SCREENINGS, "Manage Screenings"));
        page.addOption(new Option(PageType.MAIN_THEATER_EDITOR, "Manage Theaters"));
        page.addOption(new Option(PageType.LOAD_DEMO_DATA, "Load Demo Data"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_EXIT, "Quit", "Q"));

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
