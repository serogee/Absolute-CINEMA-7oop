package cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cinema.utils.CustomOption;
import cinema.utils.Option;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.ExperienceType;
import screening.Screening;
import seat.InvalidSeatException;
import seat.SeatConflictException;
import theater.Theater;
import show.Show;

class MainScreeningPages {
    private Cinema cinema;
    private Screening workingScreening;

    MainScreeningPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingScreening = null;
    }

    public void clearWorkingScreening() {
        this.workingScreening = null;
    }

    public String getReservationLayout() {
        List<String> bodyLines = new ArrayList<>();
        for (String line : Arrays.asList(this.workingScreening.getTheater().generateSeatLayoutDisplay(this.workingScreening.getResearvedSeatIDs()).split("\n"))) {
            bodyLines.add(PageBuilder.formatStringToCenter(line));
        }
        return String.join("\n", bodyLines);
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Manage Screenings");

        for (int i = 0; i < this.cinema.getScreenings().size(); i++) {
            Screening screening = this.cinema.getScreenings().get(i);
            
            page.addDisplayOption(String.format(
                "[%d] %s - %s {%d/%d seats} %s", 
                i + 1, 
                screening.getShow().toString(), 
                screening.getTheater().getName(), 
                screening.getResearvedSeatIDs().size(), 
                screening.getTheater().getRowLength() * screening.getTheater().getColumnLength(), 
                (screening == screening.getTheater().getCurrentScreening() 
                    ? "[Currently Screening]"
                    : "")
            ));
        }
        page.addCustomOption(new CustomOption(PageType.ADD_SCREENING, "Add Screening", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SCREENING, "Delete Screening", "D"));        
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getScreenings().size(),
            "Please select a valid screening number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }
        this.workingScreening = this.cinema.getScreenings().get(intInput.getValue() - 1);
        return PageResult.createResultNextPage(PageType.MANAGE_SCREENING);
    }

    public PageResult addScreeningPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Manage Screenings");
        page.setSubTitle("Add Screening");

        if (this.cinema.getTheaters().isEmpty()) {
            page.setErrorMessage("No theater found! Please create one first.");
        }

        for (int i = 0; i < this.cinema.getTheaters().size(); i++) {
            Theater theater = this.cinema.getTheaters().get(i);
            page.addDisplayOption(String.format("[%d] %s (%d rows, %d columns)", i + 1, theater.getName(), theater.getRowLength(), theater.getColumnLength()));
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        Theater selectedTheater = null;
        PageResult.Int theaterIntInput = page.nextIntResultInputLoop(
            "Select Theater",
            1,
            this.cinema.getTheaters().size(),
            "Please select a valid theater number!"
        );
        if (theaterIntInput.getPageResult() != null) {
            return theaterIntInput.getPageResult();
        } 
        selectedTheater = this.cinema.getTheaters().get(theaterIntInput.getValue() - 1);
        page.addPromptInput(theaterIntInput);

        if (this.cinema.getShows().isEmpty()) {
            page.setErrorMessage("No show found! Please create one first.");
        }

        page.clearDisplayOption();
        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            Show show = this.cinema.getShows().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, show.getShortInfo()));
        }

        Show selectedShow = null;
        PageResult.Int showIntInput = page.nextIntResultInputLoop(
            "Select Show",
            1,
            this.cinema.getShows().size(),
            "Please select a valid show number!"
        );
        if (showIntInput.getPageResult() != null) {
            return showIntInput.getPageResult();
        } 
        selectedShow = this.cinema.getShows().get(showIntInput.getValue() - 1);
        page.addPromptInput(showIntInput);

        page.clearDisplayOption();
        page.addDisplayOption("[1] Standard 2D");
        page.addDisplayOption("[2] Standard 3D");
        page.addDisplayOption("[3] IMAX");

        ExperienceType selectedExperienceType = null;
        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Select Experience Type",
            1,
            3,
            "Please enter a valid experience type number!"
        );

        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        } 
        switch (intInput.getValue()) {
            case 1:
                selectedExperienceType = ExperienceType.STANDARD_2D;
                break;
            case 2:
                selectedExperienceType = ExperienceType.STANDARD_3D;
                break;
            case 3:
                selectedExperienceType = ExperienceType.IMAX;
                break;
        }
        
        if (selectedTheater != null && selectedShow != null && selectedExperienceType != null) {
            this.cinema.getScreenings().add(new Screening(selectedTheater, selectedShow, selectedExperienceType));
        }

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult deleteScreeningPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Manage Screenings");
        page.setSubTitle("Delete Screening");

        page.setBody(PageBuilder.formatAsBody("    This will permanently delete the selected screening from the system!"));

        for (int i = 0; i < this.cinema.getScreenings().size(); i++) {
            Screening screening = this.cinema.getScreenings().get(i);
            page.addDisplayOption(String.format(
                "[%d] %s - %s {%d/%d seats} %s", 
                i + 1, 
                screening.getShow().toString(), 
                screening.getTheater().getName(), 
                screening.getResearvedSeatIDs().size(), 
                screening.getTheater().getRowLength() * screening.getTheater().getColumnLength(), 
                (screening == screening.getTheater().getCurrentScreening() 
                    ? "[Currently Screening]"
                    : "")
            ));
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Select Screening to Delete",
            1,
            this.cinema.getScreenings().size(),
            "Please select a valid screening number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        } 
        this.cinema.getScreenings().remove(intInput.getValue() - 1);
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult manageScreeningPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Manage Screening: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());

        List<String> bodyLines = new ArrayList<>();
        bodyLines.add(PageBuilder.formatBodyToCenter("{Show}"));
        bodyLines.addAll(this.workingScreening.getShow().getLongInfo());
        bodyLines.add("");
        bodyLines.add(PageBuilder.formatBodyToCenter("{Theater}"));
        bodyLines.add("Theater: " + this.workingScreening.getTheater().getName());
        bodyLines.add("Reserved: " + this.workingScreening.getResearvedSeatIDs().size() + "/" + (this.workingScreening.getTheater().getRowLength() * this.workingScreening.getTheater().getColumnLength()));
        bodyLines.add("Currently Screening: " + (this.workingScreening == this.workingScreening.getTheater().getCurrentScreening() ? "Yes" : (this.workingScreening.getTheater().getCurrentScreening() == null ? "No" : "Theater is Busy")));
        bodyLines.add("");

        page.setBody(PageBuilder.formatAsBody(bodyLines));
        page.addOption(new Option(PageType.SHOW_SCREENING_SEAT_LAYOUT, "Show Seat Reservation Layout"));
        page.addOption(new Option(PageType.ADD_RESERVATION, "Add Seat Reservation"));
        page.addOption(new Option(PageType.DELETE_RESERVATION, "Delete Seat Reservation"));
        page.addOption(new Option(PageType.EDIT_SCREENING_SHOW, "Edit Show"));
        page.addOption(new Option(PageType.EDIT_SCREENING_THEATER, "Edit Theater"));
        page.addOption(new Option(PageType.EDIT_SCREENING_EXPERIENCE_TYPE, "Edit Experience Type"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        
        return page.nextOptionResultInputLoop("Input Option");
    }

    public PageResult showSeatLayoutPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Seat Layout: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());
        
        page.setBody(getReservationLayout());

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        return page.nextOptionResultInputLoop("Input Option");
    }

    private static Pattern columnRowPattern = Pattern.compile("([a-zA-Z])(\\d+)");
    public PageResult addSeatReservationPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Add Seat Reservation: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        page.setBody(getReservationLayout());

        while (true) {
            page.display();

            PageResult.Str columnRowInput = page.nextLine("Input Column and Row (e.g. A10)");

            if (columnRowInput.getPageResult() != null) {
                return columnRowInput.getPageResult();
            }

            Matcher match = MainScreeningPages.columnRowPattern.matcher(columnRowInput.getValue());
            if (match.find()) {
                try {
                    this.workingScreening.createSeatReservation(Integer.parseInt(match.group(2)), Character.toUpperCase(match.group(1).charAt(0)));
                    return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
                } catch (SeatConflictException e1) {
                    page.setErrorMessage("'" + match.group(1) + match.group(2) + "' is already reserved!");
                } catch (InvalidSeatException e2) {
                    page.setErrorMessage("'" + match.group(1) + match.group(2) + "' is not a valid seat!");
                }
            } else {
                page.setErrorMessage("Invalid column and row input!");
            }
        }
    }

    public PageResult deleteSeatReservationPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Delete Seat Reservation: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        page.setBody(getReservationLayout());

        while (true) {
            page.display();

            PageResult.Str columnRowInput = page.nextLine("Input Column and Row (e.g. A10)");

            if (columnRowInput.getPageResult() != null) {
                return columnRowInput.getPageResult();
            }

            Matcher match = MainScreeningPages.columnRowPattern.matcher(columnRowInput.getValue());
            if (match.find()) {
                try {
                    this.workingScreening.deleteSeatReservation(Integer.parseInt(match.group(2)), Character.toUpperCase(match.group(1).charAt(0)));
                    return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
                } catch (SeatConflictException e1) {
                    page.setErrorMessage("'" + match.group(1) + match.group(2) + "' is not reserved!");
                } catch (InvalidSeatException e2) {
                    page.setErrorMessage("'" + match.group(1) + match.group(2) + "' is not a valid seat!");
                }
            } else {
                page.setErrorMessage("Invalid column and row input!");
            }
        }
    }

    public PageResult editScreeningShowPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Edit Show: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());

        for (Show show : this.cinema.getShows()) {
            page.addDisplayOption(show.toString());
        }
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int showIntInput = page.nextIntResultInputLoop(
            "Select Show",
            1,
            this.cinema.getShows().size(),
            "Please select a valid show number!"
        );
        if (showIntInput.getPageResult() != null) {
            return showIntInput.getPageResult();
        } 
        this.workingScreening.setShow(this.cinema.getShows().get(showIntInput.getValue() - 1));
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editScreeningTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Edit Theater: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());

        for (int i = 0; i < this.cinema.getTheaters().size(); i++) {
            Theater theater = this.cinema.getTheaters().get(i);
            page.addDisplayOption(String.format("[%d] %s (%d rows, %d columns)", i + 1, theater.getName(), theater.getRowLength(), theater.getColumnLength()));
        }
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int theaterIntInput = page.nextIntResultInputLoop(
            "Select Theater",
            1,
            this.cinema.getTheaters().size(),
            "Please select a valid theater number!"
        );
        if (theaterIntInput.getPageResult() != null) {
            return theaterIntInput.getPageResult();
        } 
        this.workingScreening.setTheater(this.cinema.getTheaters().get(theaterIntInput.getValue() - 1));;

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editScreeningExperienceTypePage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Screening Management");
        page.setSubTitle("Edit Experience Type: " + this.workingScreening.getShow().toString() + " | " + this.workingScreening.getTheater().getName());

        page.addDisplayOption("[1] Standard 2D");
        page.addDisplayOption("[2] Standard 3D");
        page.addDisplayOption("[3] IMAX");
        
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SCREENING);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.clearDisplayOption();
        ExperienceType selectedExperienceType = null;
        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Select Experience Type",
            1,
            3,
            "Please enter a valid experience type number!"
        );

        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        } 
        switch (intInput.getValue()) {
            case 1:
                selectedExperienceType = ExperienceType.STANDARD_2D;
                break;
            case 2:
                selectedExperienceType = ExperienceType.STANDARD_3D;
                break;
            case 3:
                selectedExperienceType = ExperienceType.IMAX;
                break;
        }

        this.workingScreening.setExperienceType(selectedExperienceType);
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }
}
