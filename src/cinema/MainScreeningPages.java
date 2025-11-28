package cinema;

import java.util.InputMismatchException;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.Screening;
import theater.Theater;
import show.Show;
import seat.SeatConflictException;
import seat.InvalidSeatException;

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
            Theater t = screening.getTheater();
            
            String status = "";
            if (t.getCurrentScreening() == screening) {
                status = " [LIVE NOW]";
            } else if (t.getCurrentScreening() != null) {
                status = " [Theater Busy]";
            }

            page.addDisplayOption(String.format(
                "[%d] %s | %s%s", 
                i + 1, 
                t.getName(), 
                screening.getShow().getTitle(),
                status
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
                    int index = intInput.getValue() - 1;
                    if (index >= 0 && index < this.cinema.getScreenings().size()) {
                        this.workingScreening = this.cinema.getScreenings().get(index);
                        result = PageResult.createResultNextPage(PageType.MANAGE_SCREENING);
                    } else {
                        page.setErrorMessage("Invalid Screening Number");
                    }
                } else {
                    result = intInput.getPageResult();
                }
            } catch (InputMismatchException e) {
                page.setErrorMessage("Please enter a valid option!");
            }
        }
        return result;
    }

    // [2] ADD SCREENING WIZARD
    public PageResult addScreeningPage() {

        PageBuilder page = new PageBuilder();
        page.setTitle("Add Screening - Step 1: Select Theater");

        if (this.cinema.getTheathers().isEmpty()) {
            System.out.println("Error: No theaters available.");
            try { Thread.sleep(1500); } catch (Exception e) {}
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, theater.getName()));
        }
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Cancel", "C"));

        Theater selectedTheater = null;
        try {
            page.display();
            PageResult.Int tInput = page.nextInt("Select Theater");
            if (tInput.getPageResult() != null) return tInput.getPageResult();
            int tIndex = tInput.getValue() - 1;
            selectedTheater = this.cinema.getTheathers().get(tIndex);
        } catch (Exception e) { return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN); }

        page = new PageBuilder(); 
        page.setTitle("Add Screening - Step 2: Select Movie");
        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            Show show = this.cinema.getShows().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, show.getTitle()));
        }

        try {
            page.display();
            PageResult.Int sInput = page.nextInt("Select Movie");
            if (sInput.getPageResult() != null) return sInput.getPageResult();
            int sIndex = sInput.getValue() - 1;
            Show selectedShow = this.cinema.getShows().get(sIndex);
            
            this.cinema.getScreenings().add(new Screening(selectedTheater, selectedShow));
            System.out.println("Screening Added!");
            Thread.sleep(1000);
        } catch (Exception e) {}

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    // [3] MANAGE SPECIFIC SCREENING (Updated with Start/End)
    public PageResult manageScreeningPage() {
        if (this.workingScreening == null) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }

        PageBuilder page = new PageBuilder();
        Theater theater = this.workingScreening.getTheater();
        Show show = this.workingScreening.getShow();

        String status = "Scheduled";
        if (theater.getCurrentScreening() == this.workingScreening) {
            status = "LIVE NOW";
        } else if (theater.getCurrentScreening() != null) {
            status = "Theater is Busy with another movie";
        }

        page.setTitle(String.format("Manage: %s (%s)", show.getTitle(), status));
        
        page.addDisplayOption("[1] Show Seat Layout");
        page.addDisplayOption("[2] Add Reservation");
        page.addDisplayOption("[3] Delete Reservation");
        page.addDisplayOption("-".repeat(20));
        page.addDisplayOption("[4] START Screening");
        page.addDisplayOption("[5] END Screening");
        
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        try {
            page.display();
            PageResult.Int choice = page.nextInt("Select Option");
            
            if (choice.getPageResult() != null) return choice.getPageResult();

            switch (choice.getValue()) {
                case 1: // Show Layout
                    PageBuilder layoutPage = new PageBuilder();
                    layoutPage.setTitle("Seat Layout");
                    layoutPage.setBody(theater.generateSeatLaoutDisplay(this.workingScreening.getResearvedSeatIDs()));
                    layoutPage.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
                    layoutPage.display();
                    layoutPage.nextOptionResult("Press Enter to Return");
                    break;

                case 2: // Add Reservation
                    addReservationFlow();
                    break;

                case 3: // Delete Reservation
                    deleteReservationFlow();
                    break;

                case 4: // START
                    try {
                        theater.startScreening(this.workingScreening);
                        System.out.println(">>> Screening STARTED! The theater is now active. <<<");
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    Thread.sleep(1500);
                    break;

                case 5: // END
                    try {
                        // We only allow ending if THIS specific screening is the one running
                        if (theater.getCurrentScreening() == this.workingScreening) {
                            theater.endScreening();
                            System.out.println(">>> Screening ENDED. Theater is now free. <<<");
                        } else {
                            System.out.println("Error: This screening is not currently running.");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    Thread.sleep(1500);
                    break;
                
                default:
                    page.setErrorMessage("Invalid Option");
            }
        } catch (Exception e) {
            page.setErrorMessage("Error: " + e.getMessage());
        }

        return null; // Loop on this page
    }

    private void addReservationFlow() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add Reservation");
        
        try {
            PageResult.Int rowRes = page.nextInt("Enter Row Number");
            if (rowRes.getPageResult() != null) return;
            int row = rowRes.getValue();

            PageResult.Char colRes = page.nextChar("Enter Column Letter");
            if (colRes.getPageResult() != null) return;
            char col = Character.toUpperCase(colRes.getValue());

            // Validate Input is a Letter
            if (!Character.isLetter(col)) {
                System.out.println("Invalid Input: Please enter a column LETTER (e.g., A, B, C).");
                Thread.sleep(1500);
                return;
            }

            this.workingScreening.createSeatReservation(row, col);
            System.out.println("Reservation Success!");
            Thread.sleep(1000);

        } catch (SeatConflictException | InvalidSeatException e) {
            System.out.println("Error: " + e.getMessage());
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        } catch (Exception e) {}
    }

    private void deleteReservationFlow() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Delete Reservation");

        try {
            PageResult.Int rowRes = page.nextInt("Enter Row Number");
            if (rowRes.getPageResult() != null) return;
            int row = rowRes.getValue();

            PageResult.Char colRes = page.nextChar("Enter Column Letter");
            if (colRes.getPageResult() != null) return;
            char col = Character.toUpperCase(colRes.getValue());

            if (!Character.isLetter(col)) {
                System.out.println("Invalid Input: Please enter a column LETTER.");
                Thread.sleep(1500);
                return;
            }

            this.workingScreening.deleteSeatReservation(row, col);
            System.out.println("Reservation Deleted!");
            Thread.sleep(1000);

        } catch (SeatConflictException | InvalidSeatException e) {
            System.out.println("Error: " + e.getMessage());
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        } catch (Exception e) {}
    }
}