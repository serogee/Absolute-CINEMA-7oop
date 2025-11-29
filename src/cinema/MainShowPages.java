package cinema;

import java.time.Duration;

import cinema.utils.CustomOption;
import cinema.utils.Option;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;
import cinema.utils.PageType;
import screening.Screening;
import show.Movie;
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
            "Input Movie Title",
            "Movie title cannot be empty!"
        );
        if (titleInput.getPageResult() != null) {
            return titleInput.getPageResult();
        }
        page.addPromptInput(titleInput);

        PageResult.Str descriptionInput = page.nextLine("Input Movie Description");
        if (descriptionInput.getPageResult() != null) {
            return descriptionInput.getPageResult();
        }
        page.addPromptInput(descriptionInput);

        PageResult.Int yearInput = page.nextIntResultInputLoop(
            "Input Movie Release Year",
            1500,
            3000,
            "Please enter a valid year!"
        );

        if (yearInput.getPageResult() != null) {
            return yearInput.getPageResult();
        }
        page.addPromptInput(yearInput);

        PageResult.Int durationInput = page.nextIntResultInputLoop(
            "Input Movie Duration (in minutes)",
            1,
            600,
            "Please enter a valid duration in minutes!"
        );
        if (durationInput.getPageResult() != null) {
            return durationInput.getPageResult();
        }

        Movie newMovie = new Movie(
            titleInput.getValue(),
            descriptionInput.getValue(),
            yearInput.getValue(),
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
