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

    public PageResult.Str inputTitle(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Title",
            "Title cannot be empty!"
        );
    }

    public PageResult.Str inputDescription(PageBuilder page) {
        return page.nextLine("Input Description");
    }

    public PageResult.Int inputReleaseYear(PageBuilder page) {
        return page.nextIntResultInputLoop(
            "Input Release Year",
            1500,
            3000,
            "Please enter a valid yearbetween 1500 and 3000!"
        );

    }

    public PageResult.Int inputDuration(PageBuilder page) {
        return page.nextIntResultInputLoop(
            "Input Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
    }

    public PageResult.Str inputLeadActor(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Lead Actor",
            "Lead Actor cannot be empty!"
        );
    }

    public PageResult.Str inputAnimationStudio(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Animation Studio",
            "Animation Studio cannot be empty!"
        );
    }

    public PageResult.Str inputArtistName(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Artist Name",
            "Artist name cannot be empty!"
        );
    }

    public PageResult mainPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
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
        page.setHeader(Config.HEADER_DISPLAY);
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
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Standard Movie");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = this.inputTitle(page);
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = this.inputDescription(page);
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = this.inputReleaseYear(page);

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Str leadActorInput = this.inputLeadActor(page);
        if (leadActorInput.getPageResult() != null) {
            return leadActorInput.getPageResult();
        }
        page.addPromptInput(leadActorInput);

        PageResult.Int durationInput = this.inputDuration(page);
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

        Movie newMovie = new Movie(
            titleInput.getValue(),
            descriptionInput.getValue(),
            yearInput.getValue(),
            leadActorInput.getValue(),
            Duration.ofMinutes(durationInput.getValue())
        );
        this.cinema.getShows().add(newMovie);

        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_MAIN);
    }

    public PageResult addShowAnimatedMoviePage() {
        
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Animated Movie");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = this.inputTitle(page);
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = this.inputDescription(page);
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = this.inputReleaseYear(page);

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Str animationStudioInput = this.inputAnimationStudio(page);
        if (animationStudioInput.getPageResult() != null) {
            return animationStudioInput.getPageResult();
        }
        page.addPromptInput(animationStudioInput);

        PageResult.Int durationInput = this.inputDuration(page);
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
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Add Concert Film");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str titleInput = this.inputTitle(page);
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = this.inputDescription(page);
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = this.inputReleaseYear(page);

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Str artistName = this.inputArtistName(page);
        if (artistName.getPageResult() != null) {
            return artistName.getPageResult();
        }
        page.addPromptInput(artistName);

        PageResult.Int durationInput = this.inputDuration(page);
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

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

    public PageResult manageShowMoviePage() {
        if (!(this.workingShow instanceof Movie)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Manage " + this.workingShow.getShowTypeAsString());
        page.setBody(PageBuilder.formatStringToCenter("{Info}") + "\n" + PageBuilder.formatAsBody(this.workingShow.getLongInfo()));
        page.addOption(new Option(PageType.EDIT_SHOW_TITLE, "Edit Title"));
        page.addOption(new Option(PageType.EDIT_SHOW_DESCRIPTION, "Edit Description"));
        page.addOption(new Option(PageType.EDIT_SHOW_RELEASE_YEAR, "Edit Release Year"));
        page.addOption(new Option(PageType.EDIT_MOVIE_LEAD_ACTOR, "Edit Lead Actor"));
        page.addOption(new Option(PageType.EDIT_SHOW_DURATION, "Edit Duration"));
        
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        return page.nextOptionResultInputLoop("Input Option");
    }

    public PageResult manageShowAnimatedMoviePage() {
        if (!(this.workingShow instanceof AnimatedMovie)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Manage " + this.workingShow.getShowTypeAsString());
        page.setBody(PageBuilder.formatStringToCenter("{Info}") + "\n" + PageBuilder.formatAsBody(this.workingShow.getLongInfo()));
        page.addOption(new Option(PageType.EDIT_SHOW_TITLE, "Edit Title"));
        page.addOption(new Option(PageType.EDIT_SHOW_DESCRIPTION, "Edit Description"));
        page.addOption(new Option(PageType.EDIT_SHOW_RELEASE_YEAR, "Edit Release Year"));
        page.addOption(new Option(PageType.EDIT_ANIMATED_MOVIE_ANIMATION_STUDIO, "Edit Animation Studio"));
        page.addOption(new Option(PageType.EDIT_SHOW_DURATION, "Edit Duration"));
        
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        return page.nextOptionResultInputLoop("Input Option");
    }

    public PageResult manageShowConcertFilmPage() {
        if (!(this.workingShow instanceof ConcertFilm)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Manage " + this.workingShow.getShowTypeAsString());
        page.setBody(PageBuilder.formatStringToCenter("{Info}") + "\n" + PageBuilder.formatAsBody(this.workingShow.getLongInfo()));
        page.addOption(new Option(PageType.EDIT_SHOW_TITLE, "Edit Title"));
        page.addOption(new Option(PageType.EDIT_SHOW_DESCRIPTION, "Edit Description"));
        page.addOption(new Option(PageType.EDIT_SHOW_RELEASE_YEAR, "Edit Release Year"));
        page.addOption(new Option(PageType.EDIT_CONCERT_FILM_ARTIST, "Edit Artist"));
        page.addOption(new Option(PageType.EDIT_SHOW_DURATION, "Edit Duration"));
        
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        return page.nextOptionResultInputLoop("Input Option");
    }

    public PageResult editShowTitlePage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Title");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();


        PageResult.Str titleInput = this.inputTitle(page);
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        this.workingShow.setTitle(titleInput.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editShowDescriptionPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Description");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();


        PageResult.Str descriptionInput = this.inputDescription(page);
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        this.workingShow.setDescription(descriptionInput.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editShowReleaseYearPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Release Year");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Int yearInput = this.inputReleaseYear(page);
        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        this.workingShow.setReleaseYear(yearInput.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editShowDurationPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Duration");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Int durationInput = this.inputDuration(page);
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }
        page.addPromptInput(durationInput);

        this.workingShow.setDuration(Duration.ofMinutes(durationInput.getValue()));
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editLeadActorPage() {
        if (!(this.workingShow instanceof Movie)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }
        Movie movie = (Movie) this.workingShow; 

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Lead Actor");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str leadActorInput = this.inputLeadActor(page);
        if (leadActorInput.getPageResult() != null) {
            return leadActorInput.getPageResult();
        }
        page.addPromptInput(leadActorInput);


        movie.setLeadActor(leadActorInput.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editAnimationStudioPage() {
        if (!(this.workingShow instanceof AnimatedMovie)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }
        AnimatedMovie animatedMovie = (AnimatedMovie) this.workingShow; 

        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Animation Studio");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str animationStudioInput = this.inputAnimationStudio(page);
        if (animationStudioInput.getPageResult() != null) {
            return animationStudioInput.getPageResult();
        }
        page.addPromptInput(animationStudioInput);


        animatedMovie.setStudio(animationStudioInput.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }

    public PageResult editArtistPage() {
        if (!(this.workingShow instanceof ConcertFilm)) {
            // Should not reach here, only for checking purposes
            throw new IllegalStateException("Show type '" + this.workingShow.getShowTypeAsString() + "' is not compatible with the method.");
        }
        ConcertFilm concertFilm = (ConcertFilm) this.workingShow; 
        
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
        page.setTitle("Show Management");
        page.setSubTitle("Edit " + this.workingShow.getShowTypeAsString() + " Artist");
        page.addCustomOption(Config.NAVIGATE_TO_PREVIOUS);
        page.addCustomOption(Config.NAVIGATE_TO_MAIN_SHOW);
        page.addCustomOption(Config.NAVIGATE_TO_START);
        page.display();

        PageResult.Str artistName = this.inputArtistName(page);
        if (artistName.getPageResult() != null) {
            return artistName.getPageResult();
        }
        page.addPromptInput(artistName);


        concertFilm.setArtist(artistName.getValue());
        return PageResult.createResultJump(PageResult.Navigation.BACK_TO_PREVIOUS);
    }



    public PageResult deleteShowPage() {
        PageBuilder page = new PageBuilder();
        page.setHud(Config.HUD_DISPLAY);
        page.setHeader(Config.HEADER_DISPLAY);
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
