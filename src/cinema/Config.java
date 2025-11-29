package cinema;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;

public class Config {

    public static int INTERFACE_WIDTH = 80;

    public static int MAX_COLUMNS = 26;

    public static int MAX_ROWS = 99;

    public static CustomOption NAVIGATE_TO_PREVIOUS = new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R");

    public static CustomOption NAVIGATE_TO_MAIN_SCREENING = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Return to Screening Management", "E");

    public static CustomOption NAVIGATE_TO_MAIN_SHOW = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Return to Show Management", "E");

    public static CustomOption NAVIGATE_TO_MAIN_THEATER = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Return to Theater Management", "E");

    public static CustomOption NAVIGATE_TO_START = new CustomOption(PageResult.Navigation.BACK_TO_START, "Main Menu", "M");
    
    public static String HUD_DISPLAY = PageBuilder.formatStringToCenter("Absolute CINEMA [7oop]");
}
