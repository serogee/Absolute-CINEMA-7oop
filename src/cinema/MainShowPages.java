package cinema;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import show.Movie;
import show.Show;
import show.utils.ExperienceType;

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
            Show show = this.cinema.getShows().get(i);
            page.addDisplayOption(String.format("[%d] %s (%d)", i + 1, show.getTitle(), show.getReleaseYear()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_SHOW, "Add Show", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SHOW, "Delete Show", "D"));
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        PageResult.Int intInput;
        PageResult result = null;
        while (result == null) {
            try {
                page.display();
                intInput = page.nextInt("Input Option");
                
                if (intInput.getPageResult() == null) { 
                    int selectionIndex = intInput.getValue() - 1;
                    if (selectionIndex >= 0 && selectionIndex < this.cinema.getShows().size()) {
                        this.workingShow = this.cinema.getShows().get(selectionIndex);
                        if (this.workingShow instanceof Movie) {
                            result = PageResult.createResultNextPage(PageType.MANAGE_SHOW_MOVIE);
                        } else {
                            page.setErrorMessage("Unknown Show Type");
                        }
                    } else {
                        page.setErrorMessage("Invalid Show Number");
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

    public PageResult addShowPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add New Movie");

        PageResult.Str titleRes = page.nextLine("Enter Movie Title");
        if (titleRes.getPageResult() != null) return titleRes.getPageResult();
        String title = titleRes.getValue();

        PageResult.Str descRes = page.nextLine("Enter Description");
        if (descRes.getPageResult() != null) return descRes.getPageResult();
        String description = descRes.getValue();

        int year = 0;
        while(true) {
            try {
                PageResult.Int yearRes = page.nextInt("Enter Release Year");
                if (yearRes.getPageResult() != null) return yearRes.getPageResult();
                year = yearRes.getValue();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid number.");
            }
        }

        Set<ExperienceType> types = new HashSet<>();
        types.add(ExperienceType.STANDARD_2D);

        this.cinema.getShows().add(new Movie(title, types, description, year));
        System.out.println("Movie Added Successfully!");
        try { Thread.sleep(800); } catch (Exception e) {} 

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult manageShowPage() {
        PageBuilder page = new PageBuilder();
        
        if (this.workingShow == null || !(this.workingShow instanceof Movie)) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
        }

        Movie movie = (Movie) this.workingShow;

        page.setTitle("Movie Details: " + movie.getTitle());
        StringBuilder body = new StringBuilder();
        body.append("Title:       ").append(movie.getTitle()).append("\n");
        body.append("Year:        ").append(movie.getReleaseYear()).append("\n");
        body.append("Experiences: ").append(movie.experiencetype.toString()).append("\n");
        body.append("-".repeat(30)).append("\n");
        body.append("Description:\n").append(movie.getDescription()).append("\n");
        page.setBody(body.toString());

        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));

        try {
            page.display();
            return page.nextOptionResult("Option");
        } catch (Exception e) {
            return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }
    }

    // --- UPDATED DELETE LOGIC HERE ---
    public PageResult deleteShowPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Delete Show");

        if (this.cinema.getShows().isEmpty()) {
            page.setBody("No shows available to delete.");
            page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
            try { page.display(); return page.nextOptionResult("Option"); } catch(Exception e) { return null; }
        }

        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            Show show = this.cinema.getShows().get(i);
            page.addDisplayOption(String.format("[%d] %s", i + 1, show.getTitle()));
        }

        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Cancel", "C"));

        try {
            page.display();
            PageResult.Int choice = page.nextInt("Select Show Number to DELETE");
            
            if (choice.getPageResult() != null) return choice.getPageResult();

            int index = choice.getValue() - 1;
            if (index >= 0 && index < this.cinema.getShows().size()) {
                Show removedShow = this.cinema.getShows().remove(index);
                
                // >>> CASCADE DELETE <<<
                // Remove all screenings that are playing this specific movie
                int originalSize = this.cinema.getScreenings().size();
                this.cinema.getScreenings().removeIf(screening -> screening.getShow().equals(removedShow));
                int deletedScreenings = originalSize - this.cinema.getScreenings().size();
                
                System.out.println("!!! DELETED MOVIE: " + removedShow.getTitle() + " !!!");
                if (deletedScreenings > 0) {
                    System.out.println("!!! Automatically removed " + deletedScreenings + " associated screenings !!!");
                }
                
                try { Thread.sleep(1500); } catch (Exception e) {}
            } else {
                page.setErrorMessage("Invalid Show Number");
            }

        } catch (Exception e) {
            // Ignore
        }
        
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }
}