package cinema;

import cinema.utils.CustomOption;
import cinema.utils.PageBuilder;
import cinema.utils.PageResult;


public class Config {
    private static String LOGO = 
"                       _    _               _       _                           \n" +
"                      / \\  | |__  ___  ___ | |_   _| |_ ___                     \n" +
"                     / _ \\ | '_ \\/ __|/ _ \\| | | | | __/ _ \\                    \n" +
"                    / ___ \\| |_) \\__ \\ (_) | | |_| | ||  __/                    \n" +
"                   /_/   \\_\\_.__/|___/\\___/|_|\\__,_|\\__\\___|                    \n" +
"             ▄▄▄▄▄▄▄ ▄▄▄▄▄ ▄▄▄    ▄▄▄  ▄▄▄▄▄▄▄ ▄▄▄      ▄▄▄   ▄▄▄▄              \n" +
"            ███▀▀▀▀▀  ███  ████▄  ███ ███▀▀▀▀▀ ████▄  ▄████ ▄██▀▀██▄            \n" +
"            ███       ███  ███▀██▄███ ███▄▄    ███▀████▀███ ███  ███            \n" +
"            ███       ███  ███  ▀████ ███      ███  ▀▀  ███ ███▀▀███            \n" +
"            ▀███████ ▄███▄ ███    ███ ▀███████ ███      ███ ███  ███            \n";

    public static int INTERFACE_WIDTH = 80;

    public static int MAX_COLUMNS = 26;

    public static int MAX_ROWS = 99;

    public static CustomOption NAVIGATE_TO_PREVIOUS = new CustomOption(PageResult.Navigation.BACK_TO_PREVIOUS, "Return", "R");

    public static CustomOption NAVIGATE_TO_MAIN_SCREENING = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Screening Management", "E");

    public static CustomOption NAVIGATE_TO_MAIN_SHOW = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Show Management", "E");

    public static CustomOption NAVIGATE_TO_MAIN_THEATER = new CustomOption(PageResult.Navigation.BACK_TO_MAIN, "Theater Management", "E");

    public static CustomOption NAVIGATE_TO_START = new CustomOption(PageResult.Navigation.BACK_TO_START, "Main Menu", "M");
    
    public static String HUD_DISPLAY = LOGO;

    public static String HEADER_DISPLAY = PageBuilder.formatStringToCenter("Project by Group 7oop for CS 211, CS-2105");
}
