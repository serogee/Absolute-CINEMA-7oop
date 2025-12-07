package cinema;

import java.time.Duration;
import java.time.Year;

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

    /**
     * Resets the working show to null.
     */
    public void clearWorkingShow() {
        this.workingShow = null;
    }

    /**
     * Asks the user for a title and returns the corresponding PageResult.
     * If the user inputs an empty title, the method will display an error message
     * and loop again until a non-empty title is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's non-empty title input
     */
    public PageResult.Str inputTitle(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Title",
            "Title cannot be empty!"
        );
    }

    /**
     * Asks the user for a description and returns the corresponding PageResult.
     * If the user inputs an empty description, the method will display an error message
     * and loop again until a non-empty description is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's non-empty description input
     */
    public PageResult.Str inputDescription(PageBuilder page) {
        return page.nextLine("Input Description");
    }

    /**
     * Asks the user for a release year and returns the corresponding PageResult.
     * If the user inputs an invalid release year (less than 1500 or greater than the current year),
     * the method will display an error message and loop again until a valid release year is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's valid release year input
     */
    public PageResult.Int inputReleaseYear(PageBuilder page) {
        return page.nextIntResultInputLoop(
            "Input Release Year",
            1500,
            Year.now().getValue(),
            "Please enter a valid yearbetween 1500 and "+ Year.now().getValue() + "!"
        );

    }

    /**
     * Asks the user for a duration in minutes and returns the corresponding PageResult.
     * If the user inputs an invalid duration (less than 1 minute or greater than 10 hours),
     * the method will display an error message and loop again until a valid duration is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's valid duration input
     */
    public PageResult.Int inputDuration(PageBuilder page) {
        return page.nextIntResultInputLoop(
            "Input Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
    }

    /**
     * Asks the user for a lead actor and returns the corresponding PageResult.
     * If the user inputs an empty lead actor, the method will display an error message
     * and loop again until a non-empty lead actor is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's non-empty lead actor input
     */
    public PageResult.Str inputLeadActor(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Lead Actor",
            "Lead Actor cannot be empty!"
        );
    }

    /**
     * Asks the user for an animation studio and returns the corresponding PageResult.
     * If the user inputs an empty animation studio, the method will display an error message
     * and loop again until a non-empty animation studio is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's non-empty animation studio input
     */
    public PageResult.Str inputAnimationStudio(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Animation Studio",
            "Animation Studio cannot be empty!"
        );
    }

    /**
     * Asks the user for an artist name and returns the corresponding PageResult.
     * If the user inputs an empty artist name, the method will display an error message
     * and loop again until a non-empty artist name is entered.
     * @param page the PageBuilder to use for display and input
     * @return the PageResult corresponding with the user's non-empty artist name input
     */
    public PageResult.Str inputArtistName(PageBuilder page) {
        return page.nextLineResultInputLoop(
            "Input Artist Name",
            "Artist name cannot be empty!"
        );
    }

    /**
     * The main page of show management.
     * Displays a list of all shows in the cinema and allows the user to select one to manage.
     * The user can also choose to add a show, delete a show, or return to the previous page.
     * @return the PageResult corresponding with the user's selected option
     */
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

    /**
     * Displays a page that allows the user to select the type of show they wish to add.
     * The user can choose from a standard movie, an animated movie, or a concert film.
     * The user can also choose to navigate to the previous page or the main menu.
     * @return the PageResult corresponding with the user's selected option
     */
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

    /**
     * Adds a standard movie to the cinema.
     * Displays a page that asks the user for the title, description, release year, and lead actor of the movie.
     * The user is also prompted to enter the duration of the movie in minutes.
     * If any of the input is invalid, an error message is displayed and the user is prompted to enter the input again.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters all of the input, the movie is added to the cinema and the user is returned to the main show page.
     * @return the PageResult corresponding with the user's selected option
     */
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

    /**
     * Adds an animated movie to the cinema.
     * Displays a page that asks the user for the title, description, release year, and animation studio of the movie.
     * The user is also prompted to enter the duration of the movie in minutes.
     * If any of the input is invalid, an error message is displayed and the user is prompted to enter the input again.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters all of the input, the movie is added to the cinema and the user is returned to the main show page.
     * @return the PageResult corresponding with the user's selected option
     */
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

    /**
     * Returns a PageResult that represents the add concert film page of the application.
     * This method generates a page that allows the user to select a title, description, release year, artist name, and duration of a concert film to add to the application.
     * After the user has entered the required information, the method adds the concert film to the application and returns a PageResult that jumps to the main show page.
     * The user can navigate to the previous page, the main show page, or the start page.
     * @return a PageResult that represents the add concert film page of the application
     */
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

    /**
     * Returns a PageResult that represents the manage show movie page of the application.
     * This method generates a page that displays the details of the selected movie, and
     * allows the user to select an option to manage the movie.
     * @return a PageResult that represents the manage show movie page of the application
     */
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

    /**
     * Returns a PageResult that represents the manage show animated movie page of the application.
     * This method generates a page that displays the details of the selected animated movie, and
     * allows the user to select an option to manage the animated movie.
     * @return a PageResult that represents the manage show animated movie page of the application
     */
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

    /**
     * Returns a PageResult that represents the manage show concert film page of the application.
     * This method generates a page that displays the details of the selected concert film, and
     * allows the user to select an option to manage the concert film.
     * @return a PageResult that represents the manage show concert film page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit show title page of the application.
     * This method generates a page that displays the details of the selected show, and
     * allows the user to edit the title of the show.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new title, the show is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit show title page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit show description page of the application.
     * This method generates a page that displays the details of the selected show, and
     * allows the user to edit the description of the show.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new description, the show is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit show description page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit show release year page of the application.
     * This method generates a page that displays the details of the selected show, and
     * allows the user to edit the release year of the show.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new release year, the show is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit show release year page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit show duration page of the application.
     * This method generates a page that displays the details of the selected show, and
     * allows the user to edit the duration of the show.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new duration, the show is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit show duration page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit lead actor page of the application.
     * This method generates a page that displays the details of the selected movie, and
     * allows the user to edit the lead actor of the movie.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new lead actor, the movie is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit lead actor page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit animation studio page of the application.
     * This method generates a page that displays the details of the selected animated movie, and
     * allows the user to edit the animation studio of the animated movie.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new animation studio, the animated movie is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit animation studio page of the application
     */
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

    /**
     * Returns a PageResult that represents the edit artist page of the application.
     * This method generates a page that displays the details of the selected concert film, and
     * allows the user to edit the artist of the concert film.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the new artist, the concert film is updated and the user is returned to the previous page.
     * @return a PageResult that represents the edit artist page of the application
     */
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

    /**
     * Returns a PageResult that represents the delete show page of the application.
     * This method generates a page that displays all shows in the cinema, and
     * allows the user to select a show to delete.
     * The user can navigate to the previous page, the main show page, or the start page.
     * If the user successfully enters the show number, the show is deleted from the application and the user is returned to the previous page.
     * @return a PageResult that represents the delete show page of the application
     */
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
