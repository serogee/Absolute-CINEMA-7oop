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

    // [1] MAIN LIST OF SCREENINGS
    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Screenings");

        if (this.cinema.getScreenings().isEmpty()) {
            page.setBody("No screenings scheduled.");
        }

        for (int i = 0; i < this.cinema.getScreenings().size(); i++) {
            Screening screening = this.cinema.getScreenings().get(i);
            
            page.addDisplayOption(String.format(
                "[%d] %s | %s (%d)", 
                i + 1, 
                screening.getTheater().getName(), 
                screening.getShow().getTitle(),   
                screening.getShow().getReleaseYear()
            ));
        }
        page.addCustomOption(new CustomOption(PageType.ADD_SCREENING, "Add Screening", "A"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result = null;
        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                if (intInput.getPageResult() == null) {
                     page.setErrorMessage("Viewing details not implemented yet.");
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

    // [2] ADD SCREENING WIZARD
    public PageResult addScreeningPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add Screening - Step 1: Select Theater");

        if (this.cinema.getTheathers().isEmpty()) {
            System.out.println("Error: No theaters available. Please add a theater first.");
            try { Thread.sleep(1500); } catch (Exception e) {}
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption(String.format("[%d] %s (Seats: %d)", i + 1, theater.getName(), theater.getRowLength() * theater.getColumnLength()));
        }
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Cancel", "C"));

        // Step 1: Pick Theater
        Theater selectedTheater = null;
        try {
            page.display();
            PageResult.Int tInput = page.nextInt("Select Theater");
            if (tInput.getPageResult() != null) return tInput.getPageResult();
            
            int tIndex = tInput.getValue() - 1;
            if (tIndex >= 0 && tIndex < this.cinema.getTheathers().size()) {
                selectedTheater = this.cinema.getTheathers().get(tIndex);
            } else {
                throw new InputMismatchException("Invalid index");
            }
        } catch (Exception e) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        // Step 2: Pick Show
        // FIX: Create a NEW PageBuilder instead of trying to clear the old one
        page = new PageBuilder(); 
        page.setTitle("Add Screening - Step 2: Select Movie");

        if (this.cinema.getShows().isEmpty()) {
            System.out.println("Error: No movies available. Please add a movie first.");
            try { Thread.sleep(1500); } catch (Exception e) {}
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            Show show = this.cinema.getShows().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, show.getTitle()));
        }

        Show selectedShow = null;
        try {
            page.display();
            PageResult.Int sInput = page.nextInt("Select Movie");
            if (sInput.getPageResult() != null) return sInput.getPageResult();

            int sIndex = sInput.getValue() - 1;
            if (sIndex >= 0 && sIndex < this.cinema.getShows().size()) {
                selectedShow = this.cinema.getShows().get(sIndex);
            } else {
                throw new InputMismatchException("Invalid index");
            }
        } catch (Exception e) {
             return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        // Create Screening
        if (selectedTheater != null && selectedShow != null) {
            this.cinema.getScreenings().add(new Screening(selectedTheater, selectedShow));
            System.out.println("Screening Added Successfully!");
            try { Thread.sleep(1000); } catch (Exception e) {}
        }
        
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }
}