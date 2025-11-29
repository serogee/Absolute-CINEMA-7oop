package cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cinema.utils.CustomOption;
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

    public void clearWorkingTheater() {
        this.workingTheater = null;
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Theater Management");

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption(String.format("[%d] %s (%d rows, %d columns)", i + 1, theater.getName(), theater.getRowLength(), theater.getColumnLength()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_THEATER, "Add Theater", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_THEATER, "Delete Theater", "D"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getTheathers().size(),
            "Please select a valid theater number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }
        this.workingTheater = this.cinema.getTheathers().get(intInput.getValue() - 1);
        return PageResult.createResultNextPage(PageType.MANAGE_THEATER);
    }

    public PageResult addTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
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
        this.cinema.getTheathers().add(newTheater);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult deleteTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Delete Theater");
        page.setBody(PageBuilder.formatAsBody("    This will permanently delete the selected theater from the system, including all associated screenings!"));;

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption("[" + (i + 1) + "] Theater: " + theater.getName() + " | Seats: " + (theater.getRowLength() * theater.getColumnLength()));
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getTheathers().size(),
            "Please select a valid theater number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        }

        int indexToDelete = intInput.getValue() - 1;
        for (Screening screening : this.cinema.getScreenings()) {
            if (screening.getTheater() == this.cinema.getTheathers().get(indexToDelete)) {
                this.cinema.getScreenings().remove(screening);
            }
        }
        this.cinema.getTheathers().remove(indexToDelete);
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult manageTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
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
            page.addCustomOption(new CustomOption(PageType.SET_CURRENT_SCREENING, "End Current Screening", "A"));
        } else {
            page.addCustomOption(new CustomOption(PageType.SET_CURRENT_SCREENING, "Start Screening", "A"));
        }

        page.addCustomOption(new CustomOption(PageType.SHOW_THEATER_SEAT_LAYOUT, "Show Seat Layout", "S"));
        page.addCustomOption(new CustomOption(PageType.EDIT_THEATER_DIMENSIONS, "Edit Theater Dimensions", "D"));
        page.addCustomOption(new CustomOption(PageType.EDIT_THEATER_NAME, "Edit Theater Name", "F"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        return page.nextOptionResultInputLoop("Input Option");
    }

    public PageResult showTheaterSeatLayoutPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
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

    public PageResult setCurrentScreeningPage() {

        if (this.workingTheater.getCurrentScreening() != null) {
            this.workingTheater.endScreening();
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Theater Management");
        page.setSubTitle("Set Current Screening for Theater: " + this.workingTheater.getName());

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

    public PageResult editTheaterNamePage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
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

    public PageResult editTheaterDimensionsPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
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
