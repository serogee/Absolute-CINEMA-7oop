package cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cinema.utils.CustomOption;
import cinema.utils.Option;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.Screening;
import theater.Theater;

class MainTheaterPages {
    Cinema cinema;
    Theater workingTheater;

    public MainTheaterPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingTheater = null;
    }

    /**
     * Clears the working theater to null.
     * This method effectively cancels any theater management operations that were in progress.
     */
    public void clearWorkingTheater() {
        this.workingTheater = null;
    }

    /**
     * Returns a PageResult that represents the main page of the theater management section.
     * This method generates a page that displays all theaters in the cinema, and allows the user to select an option to add a theater, delete a theater, or manage an existing theater.
     * @return a PageResult that represents the main page of the theater management section
     */
    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");

        for (int i = 0; i < this.cinema.getTheaters().size(); i++) {
            Theater theater = this.cinema.getTheaters().get(i);
            page.addDisplayOption(String.format("[%d] %s (%d rows, %d columns)", i + 1, theater.getName(), theater.getRowLength(), theater.getColumnLength()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_THEATER, "Add Theater", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_THEATER, "Delete Theater", "D"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getTheaters().size(),
            "Please select a valid theater number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }
        this.workingTheater = this.cinema.getTheaters().get(intInput.getValue() - 1);
        return PageResult.createResultNextPage(PageType.MANAGE_THEATER);
    }

    /**
     * Returns a PageResult that represents the add theater page of the application.
     * This method generates a page that allows the user to input the name of the theater and the dimensions of the theater.
     * After the user inputs the required information, the user is returned to the main page and the new theater is added to the cinema.
     * @return a PageResult that represents the add theater page of the application
     */
    public PageResult addTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Add Theater");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        page.display();
        PageResult.Str strInput = page.nextLineResultInputLoop(
            "Input Theater Name",
            "Theater name cannot be empty!"
        );
        page.addPromptInput(strInput);

        if (strInput.getPageResult() != null) {
            return strInput.getPageResult();
        } 

        PageResult.Int rowLength = page.nextIntResultInputLoop(
            "Input Row Length",
            1,
            Config.MAX_ROWS,
            "Row length must be between 1 and " + Config.MAX_ROWS + "!"
        );
        if (rowLength.getPageResult() != null) {
            return rowLength.getPageResult();
        }
        page.addPromptInput(rowLength);


        PageResult.Int columnLength = page.nextIntResultInputLoop(
            "Input Column Length",
            1,
            Config.MAX_COLUMNS,
            "Column length must be between 1 and " + Config.MAX_COLUMNS + "!"
        );
        if (columnLength.getPageResult() != null) {
            return columnLength.getPageResult();
        }
        
        Theater newTheater = new Theater(strInput.getValue(), rowLength.getValue(), columnLength.getValue());
        this.cinema.getTheaters().add(newTheater);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    /**
     * Returns a PageResult that represents the delete theater page of the application.
     * This method generates a page that displays all theaters in the cinema, and allows the user to select a theater to delete.
     * After the user selects a theater, the theater is permanently deleted from the system, including all associated screenings.
     * @return a PageResult that represents the delete theater page of the application
     */
    public PageResult deleteTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Delete Theater");
        page.setBody(PageBuilder.formatAsBody("    This will permanently delete the selected theater from the system, including all associated screenings!"));;

        for (int i = 0; i < this.cinema.getTheaters().size(); i++) {
            Theater theater = this.cinema.getTheaters().get(i);
            page.addDisplayOption("[" + (i + 1) + "] Theater: " + theater.getName() + " | Seats: " + (theater.getRowLength() * theater.getColumnLength()));
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getTheaters().size(),
            "Please select a valid theater number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }

        int indexToDelete = intInput.getValue() - 1;
        for (Screening screening : this.cinema.getScreenings()) {
            if (screening.getTheater() == this.cinema.getTheaters().get(indexToDelete)) {
                this.cinema.getScreenings().remove(screening);
            }
        }
        this.cinema.getTheaters().remove(indexToDelete);
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    /**
     * Returns a PageResult that represents the manage theater page of the application.
     * This method generates a page that displays the details of the selected theater, and
     * allows the user to select an option to manage the theater.
     * The user can select options to end or start a screening, show the theater seat layout,
     * edit the theater dimensions, edit the theater name, or navigate to the previous page, the main
     * theater page, or the start page.
     * @return a PageResult that represents the manage theater page of the application
     */
    public PageResult manageTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Manage Theater: " + this.workingTheater.getName());

        List<String> bodyLines = new ArrayList<>();
        bodyLines.add("Dimensions: " + this.workingTheater.getRowLength() + " rows x " + this.workingTheater.getColumnLength() + " columns");
        bodyLines.add("Total Seats: " + (this.workingTheater.getRowLength() * this.workingTheater.getColumnLength()));

        Screening screening = this.workingTheater.getCurrentScreening();
        if (screening != null) {
            bodyLines.add(PageBuilder.formatBodyToCenter("{Currently Screening}"));
            bodyLines.addAll(screening.getShow().getLongInfo());
            bodyLines.add("Experience Type: " + screening.getExperienceType());
        } else {
            bodyLines.add("Currently Screening: None");
        }
        bodyLines.add("");
        page.setBody(PageBuilder.formatAsBody(bodyLines));

        if (screening != null) {
            page.addOption(new Option(PageType.SET_CURRENT_SCREENING, "End Current Screening"));
        } else {
            page.addOption(new Option(PageType.SET_CURRENT_SCREENING, "Start Screening"));
        }

        page.addOption(new Option(PageType.SHOW_THEATER_SEAT_LAYOUT, "Show Seat Layout"));
        page.addOption(new Option(PageType.EDIT_THEATER_DIMENSIONS, "Edit Theater Dimensions"));
        page.addOption(new Option(PageType.EDIT_THEATER_NAME, "Edit Theater Name"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        return page.nextOptionResultInputLoop("Input Option");
    }

    /**
     * Returns a PageResult that represents the show theater seat layout page of the application.
     * This method generates a page that displays the seat layout of the selected theater, and
     * allows the user to navigate to the previous page, the main theater page, or the start page.
     * @return a PageResult that represents the show theater seat layout page of the application
     */
    public PageResult showTheaterSeatLayoutPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Seat Layout for Theater: " + this.workingTheater.getName());
        
        List<String> bodyLines = new ArrayList<>();
        for (String line : Arrays.asList(this.workingTheater.generateSeatLayoutDisplay().split("\n"))) {
            bodyLines.add(PageBuilder.formatStringToCenter(line));
        }
        page.setBody(String.join("\n", bodyLines));

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_THEATER);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        return page.nextOptionResultInputLoop("Input Option");
    }

    /**
     * Returns a PageResult that represents the set current screening page of the application.
     * This method generates a page that allows the user to select a screening to set as the current
     * screening for the selected theater, and allows the user to navigate to the previous page, the main
     * theater page, or the start page.
     * If the selected theater is already currently screening a show, this method will end the current
     * screening and return the user to the previous page.
     * If there are no screenings available to select from, this method will display an error message to
     * the user.
     * @return a PageResult that represents the set current screening page of the application
     */
    public PageResult setCurrentScreeningPage() {

        if (this.workingTheater.getCurrentScreening() != null) {
            this.workingTheater.endScreening();
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Set Current Screening for Theater: " + this.workingTheater.getName());

        if (this.cinema.getScreenings().isEmpty()) {
            page.setErrorMessage("No screening found! Please create one first.");
        }

        List<Screening> availableScreenings = new ArrayList<>();
        for (Screening screening : this.cinema.getScreenings()) {
            if (screening.getTheater() == this.workingTheater) {
                availableScreenings.add(screening);
            }
        }

        for (int i = 0; i < availableScreenings.size(); i++) {
            Screening screening = availableScreenings.get(i);
            page.addDisplayOption("[" + (i + 1) + "] " + screening.getShow().toString() + " | " + screening.getShow().getShowTypeAsString());
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            availableScreenings.size(),
            "Please select a valid screening number!"
        );

        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }

        int index = intInput.getValue() - 1;
        this.workingTheater.startScreening(availableScreenings.get(index));
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    /**
     * Allows the user to edit the name of the selected theater.
     * The user is prompted to enter the new theater name.
     * If the input is valid, the theater name is updated and the user is returned to the previous page.
     * If the input is invalid, an error message is displayed and the user is prompted to enter the input again.
     * The user can navigate to the previous page, the main theater page, or the start page.
     * @return the result of the page navigation loop
     */
    public PageResult editTheaterNamePage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Edit Theater Name");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Str strInput = page.nextLineResultInputLoop(
            "Input New Theater Name",
            "Theater name cannot be empty!"
        );

        if (strInput.getPageResult() != null) {
            return strInput.getPageResult();
        }
        this.workingTheater.setName(strInput.getValue()); 
        
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    /**
     * Allows the user to edit the dimensions of the selected theater.
     * The user is prompted to enter the new row length and column length.
     * If the input is valid, the theater dimensions are updated and all associated screenings are cleared.
     * If the input is invalid, an error message is displayed and the user is prompted to enter the input again.
     * The user can navigate to the previous page, the main theater page, or the start page.
     * @return the result of the page navigation loop
     */
    public PageResult editTheaterDimensionsPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Edit Theater Dimensions");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int rowLength = page.nextIntResultInputLoop(
            "Input New Row Length",
            1,
            Config.MAX_ROWS,
            "Row length must be between 1 and " + Config.MAX_ROWS + "!"
        );
        if (rowLength.getPageResult() != null) {
            return rowLength.getPageResult();
        }
        page.addPromptInput(rowLength);

        PageResult.Int columnLength = page.nextIntResultInputLoop(
            "Input New Column Length",
            1,
            Config.MAX_COLUMNS,
            "Column length must be between 1 and " + Config.MAX_COLUMNS + "!"
        );
        if (columnLength.getPageResult() != null) {
            return columnLength.getPageResult();
        }

        for (Screening screening : this.cinema.getScreenings()) {
            if (screening.getTheater() == this.workingTheater) {
                screening.clearSeatReservations();
            }
        }
        this.workingTheater.setSeatLayoutDimensions(rowLength.getValue(), columnLength.getValue());

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

}
