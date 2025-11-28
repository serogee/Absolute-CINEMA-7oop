package cinema;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import show.AnimatedMovie;
import show.ConcertFilm;
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

    // [1] MAIN LIST - Handles the Routing Logic
    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Shows");

        // List all shows together
        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            Show show = this.cinema.getShows().get(i);
            
            // Visual helper to see type in the list
            String typeLabel = "Movie";
            if (show instanceof AnimatedMovie) typeLabel = "Animation";
            else if (show instanceof ConcertFilm) typeLabel = "Concert";

            page.addDisplayOption(String.format("[%d] %s (%d) [%s]", i + 1, show.getTitle(), show.getReleaseYear(), typeLabel));
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
                        
                        // Set the show we are working on
                        this.workingShow = this.cinema.getShows().get(selectionIndex);
                        
                        if (this.workingShow instanceof AnimatedMovie) {
                            result = PageResult.createResultNextPage(PageType.MANAGE_SHOW_ANIMATED_MOVIE);
                        } else if (this.workingShow instanceof ConcertFilm) {
                            result = PageResult.createResultNextPage(PageType.MANAGE_SHOW_CONCERT_FILM);
                        } else if (this.workingShow instanceof Movie) {
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

    // [2] SELECTION HUB FOR ADDING
    public PageResult selectShowTypePage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Select Show Type to Add");
        
        page.addDisplayOption("[1] Standard Movie");
        page.addDisplayOption("[2] Animated Movie");
        page.addDisplayOption("[3] Concert Film");
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Cancel", "C"));

        try {
            page.display();
            PageResult.Int choice = page.nextInt("Select Type");
            if (choice.getPageResult() != null) return choice.getPageResult();

            switch (choice.getValue()) {
                case 1: return PageResult.createResultNextPage(PageType.ADD_SHOW_MOVIE);
                case 2: return PageResult.createResultNextPage(PageType.ADD_SHOW_ANIMATED_MOVIE);
                case 3: return PageResult.createResultNextPage(PageType.ADD_SHOW_CONCERT_FILM);
                default: page.setErrorMessage("Invalid Selection");
            }
        } catch (Exception e) {
             return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        }
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    // --- ADDING METHODS ---

    public PageResult addMoviePage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add New Movie");
        
        String title = page.nextLine("Title").getValue();
        String desc = page.nextLine("Description").getValue();
        int year = getValidInt(page, "Release Year");

        Set<ExperienceType> types = new HashSet<>();
        types.add(ExperienceType.STANDARD_2D);

        this.cinema.getShows().add(new Movie(title, types, desc, year));
        System.out.println("Movie Added!");
        try { Thread.sleep(800); } catch(Exception e){}
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult addAnimatedMoviePage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add Animated Movie");
        
        String title = page.nextLine("Title").getValue();
        
        String studio = page.nextLine("Animation Studio").getValue(); 
        String desc = page.nextLine("Description").getValue();
        int year = getValidInt(page, "Release Year");

        Set<ExperienceType> types = new HashSet<>();
        types.add(ExperienceType.STANDARD_2D);

        this.cinema.getShows().add(new AnimatedMovie(title, types, desc, year, studio));
        System.out.println("Animated Movie Added!");
        try { Thread.sleep(800); } catch(Exception e){}
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult addConcertFilmPage() {
        PageBuilder page = new PageBuilder();
        page.setTitle("Add Concert Film");

        String title = page.nextLine("Title").getValue();
        
        String artist = page.nextLine("Artist/Band").getValue();
        String desc = page.nextLine("Description").getValue();
        int year = getValidInt(page, "Release Year");

        Set<ExperienceType> types = new HashSet<>();
        types.add(ExperienceType.STANDARD_2D);

        this.cinema.getShows().add(new ConcertFilm(title, types, desc, year, artist));
        System.out.println("Concert Film Added!");
        try { Thread.sleep(800); } catch(Exception e){}
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult manageMoviePage() {
        if (!(this.workingShow instanceof Movie)) return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        
        Movie movie = (Movie) this.workingShow;
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Movie: " + movie.getTitle());
        
        StringBuilder body = new StringBuilder();
        body.append("Type: Standard Movie\n");
        body.append("Year: ").append(movie.getReleaseYear()).append("\n");
        body.append("Desc: ").append(movie.getDescription());
        
        page.setBody(body.toString());
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
        
        try { page.display(); return page.nextOptionResult("Option"); } catch (Exception e) { return null; }
    }

    public PageResult manageAnimatedMoviePage() {
        if (!(this.workingShow instanceof AnimatedMovie)) return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        
        AnimatedMovie anim = (AnimatedMovie) this.workingShow;
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Animation: " + anim.getTitle());
        
        StringBuilder body = new StringBuilder();
        body.append("Type:   Animated Movie\n");
        // Displaying the unique field
        body.append("Studio: ").append(anim.getStudio()).append("\n"); 
        body.append("Year:   ").append(anim.getReleaseYear()).append("\n");
        body.append("Desc:   ").append(anim.getDescription());
        
        page.setBody(body.toString());
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
        
        try { page.display(); return page.nextOptionResult("Option"); } catch (Exception e) { return null; }
    }

    public PageResult manageConcertFilmPage() {
        if (!(this.workingShow instanceof ConcertFilm)) return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
        
        ConcertFilm concert = (ConcertFilm) this.workingShow;
        PageBuilder page = new PageBuilder();
        page.setTitle("Manage Concert: " + concert.getTitle());
        
        StringBuilder body = new StringBuilder();
        body.append("Type:   Concert Film\n");
        // Displaying the unique field
        body.append("Artist: ").append(concert.getArtist()).append("\n");
        body.append("Year:   ").append(concert.getReleaseYear()).append("\n");
        body.append("Desc:   ").append(concert.getDescription());
        
        page.setBody(body.toString());
        page.addCustomOption(new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R"));
        
        try { page.display(); return page.nextOptionResult("Option"); } catch (Exception e) { return null; }
    }

    public PageResult deleteShowPage() {
        System.out.println("Delete functionality placeholder.");
        try { Thread.sleep(1000); } catch(Exception e){}
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    private int getValidInt(PageBuilder page, String prompt) {
        while(true) {
            try {
                PageResult.Int res = page.nextInt(prompt);
                if (res.getPageResult() != null) return 0; // Handle cancel
                return res.getValue();
            } catch (InputMismatchException e) { System.out.println("Invalid number."); }
        }
    }
}