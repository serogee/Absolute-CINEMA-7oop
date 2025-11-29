package cinema;

import java.time.Duration;

import cinema.utils.CustomOption;
import cinema.utils.Option;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.Screening;
import show.Movie;
import show.AnimatedMovie;
import show.ConcertFilm;
import show.Show;

class MainShowPages {
    private Cinema cinema;
    private Show workingShow;

    MainShowPages(Cinema cinema) {
        this.cinema = cinema;
        this.workingShow = null;
    }

    public void clearWorkingShow() {
        this.workingShow = null;
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");

        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            page.addDisplayOption(String.format("[%d] %s", i + 1, this.cinema.getShows().get(i).getShortInfo()));
        }

        page.addCustomOption(new CustomOption(PageType.ADD_SHOW, "Add Show", "A"));
        page.addCustomOption(new CustomOption(PageType.DELETE_SHOW, "Delete Show", "D"));
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getShows().size(),
            "Please select a valid show number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        } 
        this.workingShow = this.cinema.getShows().get(intInput.getValue() - 1);
        if (this.workingShow instanceof Movie) {
            return PageResult.createResultNextPage(PageType.MANAGE_SHOW_MOVIE);
        } else if (this.workingShow instanceof AnimatedMovie) {
            return PageResult.createResultNextPage(PageType.MANAGE_SHOW_ANIMATED_MOVIE);
        } else if (this.workingShow instanceof ConcertFilm) {
            return PageResult.createResultNextPage(PageType.MANAGE_SHOW_CONCERT_FILM);
        } else {
            // Should not reach here
            throw new IllegalStateException("Unsupported show type");
        }
    }

    public PageResult addShowPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Show: Select Show Type");
        page.addOption(new Option(PageType.ADD_SHOW_MOVIE, "Standard Movie"));
        page.addOption(new Option(PageType.ADD_SHOW_ANIMATED_MOVIE, "Animated Movie"));
        page.addOption(new Option(PageType.ADD_SHOW_CONCERT_FILM, "Concert Film"));

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult result = page.nextOptionResultInputLoop("Input Option");

        return result;
    }

    public PageResult addShowMoviePage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Movie");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = page.nextLineResultInputLoop(
            "Input Title",
            "Ritle cannot be empty!"
        );
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = page.nextLine("Input Description");
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = page.nextIntResultInputLoop(
            "Input Release Year",
            1500,
            3000,
            "Please enter a valid yearbetween 1500 and 3000!"
        );

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Int durationInput = page.nextIntResultInputLoop(
            "Input Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

        Movie newMovie = new Movie(
            titleInput.getValue(),
            descriptionInput.getValue(),
            yearInput.getValue(),
            Duration.ofMinutes(durationInput.getValue())
        );
        this.cinema.getShows().add(newMovie);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult addShowAnimatedMoviePage() {
        
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Animated Movie");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = page.nextLineResultInputLoop(
            "Input Title",
            "Title cannot be empty!"
        );
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = page.nextLine("Input Description");
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = page.nextIntResultInputLoop(
            "Input Release Year",
            1500,
            3000,
            "Please enter a valid year between 1500 and 3000!"
        );

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Str animationStudioInput = page.nextLineResultInputLoop(
            "Input Animation Studio",
            "Animation Studio cannot be empty!"
        );
        if (animationStudioInput.getPageResult() != null) {
            return animationStudioInput.getPageResult();
        }
        page.addPromptInput(animationStudioInput);

        PageResult.Int durationInput = page.nextIntResultInputLoop(
            "Input Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

        AnimatedMovie newMovie = new AnimatedMovie(
            titleInput.getValue(),
            descriptionInput.getValue(),
            yearInput.getValue(),
            animationStudioInput.getValue(),
            Duration.ofMinutes(durationInput.getValue())
        );
        this.cinema.getShows().add(newMovie);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult addShowConcertFilmPage() {
        
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Concert Film");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = page.nextLineResultInputLoop(
            "Input Title",
            "Title cannot be empty!"
        );
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = page.nextLine("Input Description");
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = page.nextIntResultInputLoop(
            "Input Release Year",
            1500,
            3000,
            "Please enter a valid year between 1500 and 3000!"
        );

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Int durationInput = page.nextIntResultInputLoop(
            "Input Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

        PageResult.Str artistName = page.nextLineResultInputLoop(
            "Input Artist Name",
            "Artist name cannot be empty!"
        );
        if (artistName.getPageResult() != null) {
            return artistName.getPageResult();
        }
        page.addPromptInput(artistName);

        ConcertFilm newMovie = new ConcertFilm(
            titleInput.getValue(),
            descriptionInput.getValue(),
            yearInput.getValue(),
            artistName.getValue(),
            Duration.ofMinutes(durationInput.getValue())
        );
        this.cinema.getShows().add(newMovie);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult deleteShowPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Delete Show");

        for (int i = 0; i < this.cinema.getShows().size(); i++) {
            page.addDisplayOption(String.format("[%d] %s", i + 1, this.cinema.getShows().get(i).toString()));
        }

        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_START);

        PageResult.Int intInput = page.nextIntResultInputLoop(
            "Input Option",
            1,
            this.cinema.getShows().size(),
            "Please select a valid show number!"
        );
        if (intInput.getPageResult() != null) {
            return intInput.getPageResult();
        } 

        int indexToDelete = intInput.getValue() - 1;
        for (Screening screening : this.cinema.getScreenings()) {
            if (screening.getShow() == this.cinema.getShows().get(indexToDelete)) {
                this.cinema.getScreenings().remove(screening);
            }
        }
        this.cinema.getShows().remove(indexToDelete);
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }
}
