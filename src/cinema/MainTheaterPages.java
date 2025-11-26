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

    // [1] LIST THEATERS PAGE
    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Theater Management");

        if (this.cinema.getTheathers().isEmpty()) {
            page.setBody("No theaters found.");
        }

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption(String.format(
                "[%d] %s | Capacity: %d seats (%dx%d)", 
                i + 1, 
                theater.getName(), 
                theater.getRowLength() * theater.getColumnLength(),
                theater.getRowLength(),
                theater.getColumnLength()
            ));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_THEATER, "Add Theater", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_THEATER, "Delete Theater", "D"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result = null;
        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                
                if (intInput.getPageResult() == null) { 
                    int index = intInput.getValue() - 1;
                    if (index >= 0 && index < this.cinema.getTheathers().size()) {
                        this.workingTheater = this.cinema.getTheathers().get(index);
                        result = PageResult.createResultNextPage(PageType.MANAGE_THEATER);
                    } else {
                        page.setErrorMessage("Invalid Theater Number");
                    }
                } else {
                    result = intInput.getPageResult();
                }
            } catch (Exception e) {
                page.setErrorMessage("Invalid Input: " + e.getMessage());
            }
        }
        return result;
    }

    // [2] ADD THEATER PAGE
    public PageResult addTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add New Theater");

        // 1. Get Name
        PageResult.Str nameRes = page.nextLine("Enter Theater Name");
        if (nameRes.getPageResult() != null) return nameRes.getPageResult();
        String name = nameRes.getValue();

        // 2. Get Rows
        int rows = 0;
        while (true) {
            try {
                PageResult.Int rowRes = page.nextInt("Enter Number of Rows");
                if (rowRes.getPageResult() != null) return rowRes.getPageResult();
                rows = rowRes.getValue();
                if (rows > 0) break;
                System.out.println("Rows must be greater than 0.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid number.");
            }
        }

        // 3. Get Columns
        int cols = 0;
        while (true) {
            try {
                PageResult.Int colRes = page.nextInt("Enter Number of Columns");
                if (colRes.getPageResult() != null) return colRes.getPageResult();
                cols = colRes.getValue();
                if (cols > 0) break;
                System.out.println("Columns must be greater than 0.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid number.");
            }
        }

        // 4. Create
        this.cinema.getTheathers().add(new Theater(name, rows, cols));
        System.out.println("Theater Added Successfully!");
        try { Thread.sleep(800); } catch (Exception e) {}

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    // [3] MANAGE / VIEW DETAILS PAGE
    public PageResult manageTheaterPage() {
        PageBuilder page = new PageBuilder();
        
        if (this.workingTheater == null) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        page.setTitle("Theater Details: " + this.workingTheater.getName());
        
        StringBuilder body = new StringBuilder();
        body.append("Name:      ").append(this.workingTheater.getName()).append("\n");
        body.append("Dimensions: ").append(this.workingTheater.getRowLength())
            .append(" rows x ").append(this.workingTheater.getColumnLength()).append(" columns\n");
        body.append("Total Seats:").append(this.workingTheater.getRowLength() * this.workingTheater.getColumnLength()).append("\n");
        body.append("-".repeat(30)).append("\n");
        body.append("Seat Layout Preview:\n");
        
        // This generates the visual map defined in Theater.java
        body.append(this.workingTheater.generateSeatLayoutDisplay());

        page.setBody(body.toString());

        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        try {
            page.display();
            return page.nextOptionResult("Option");
        } catch (Exception e) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }
    }

    // [4] DELETE THEATER PAGE
    public PageResult deleteTheaterPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Delete Theater");

        if (this.cinema.getTheathers().isEmpty()) {
            page.setBody("No theaters available to delete.");
            page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
            try { page.display(); return page.nextOptionResult("Option"); } catch(Exception e) { return null; }
        }

        for (int i = 0; i < this.cinema.getTheathers().size(); i++) {
            Theater theater = this.cinema.getTheathers().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, theater.getName()));
        }

        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Cancel", "C"));

        try {
            page.display();
            PageResult.Int choice = page.nextInt("Select Theater to DELETE");
            
            if (choice.getPageResult() != null) return choice.getPageResult();

            int index = choice.getValue() - 1;
            if (index >= 0 && index < this.cinema.getTheathers().size()) {
                Theater removed = this.cinema.getTheathers().remove(index);
                
                // CASCADE DELETE: Remove all screenings in this theater
                int originalSize = this.cinema.getScreenings().size();
                this.cinema.getScreenings().removeIf(s -> s.getTheater().equals(removed));
                int deletedScreenings = originalSize - this.cinema.getScreenings().size();

                System.out.println("!!! DELETED: " + removed.getName() + " !!!");
                if (deletedScreenings > 0) {
                    System.out.println("!!! Automatically cancelled " + deletedScreenings + " screenings in this theater !!!");
                }
                try { Thread.sleep(1500); } catch (Exception e) {}
            } else {
                page.setErrorMessage("Invalid Theater Number");
            }

        } catch (Exception e) {
            // Ignore
        }
        
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }
}