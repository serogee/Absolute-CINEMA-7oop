package cinema.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cinema.Config;

public class PageBuilder {
    
    // Should only be created once and shared across all PageBuilder instances
    private static Scanner scanner = new Scanner(System.in);

    // Should only be closed once when the application exits
    public static void closeScanner() {
        if (PageBuilder.scanner != null) {
            PageBuilder.scanner.close();
            PageBuilder.scanner = null;
        }
    }

    private String hud = "";
    private String header = "";
    private String title = "";
    private String subTitle = "";
    private String errorMessage = "";
    private String body = "";
    private List<Option> options = new ArrayList<>();
    private List<String> displayOptions = new ArrayList<>();
    private List<CustomOption> customOptions = new ArrayList<>();
    private Option enterOption = null;
    private List<String> promptInputHistory = new ArrayList<>();

    // Mutators

    public void setHud(String hud) { this.hud = hud; }
    public void setHeader(String header) { this.header = header; }
    public void setTitle(String title) { this.title = title; }
    public void setSubTitle(String subTitle) { this.subTitle = subTitle; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void clearErrorMessage() { this.errorMessage = ""; }
    public void setBody(String body) { this.body = body; }
    public void addOption(Option option) { this.options.add(option); }
    public void addDisplayOption(String displayOption) { this.displayOptions.add(displayOption); }
    public void clearDisplayOption() { this.displayOptions.clear(); }
    public void addCustomOption(CustomOption customOption) { this.customOptions.add(customOption); }
    public void setEnterOption(Option enterOption) { this.enterOption = enterOption; }
    public void clearPromptInputHistory() { this.promptInputHistory.clear(); }
    
    /**
     * Adds a record of the user's input to the prompt input history.
     *
     * @param input The input provided by the user in response to a prompt.
     */
    public void addPromptInput(PageResult.DataType input) { 
        this.promptInputHistory.add("  >> " + input.getPrompt() + ": " + input.toString()); 
    }

    // Utilities
    
    /**
     * Formats a given string as a body of text, with each line indented to the left
     * and right by 2 spaces, and wrapped to fit within the interface width.
     * 
     * @param line The string to format as a body of text.
     * @return The formatted string.
     */
    public static String formatAsBody(String line) {
        return PageBuilder.formatAsBody(Arrays.asList(line.split("\n")));
    }

    /**
     * Formats a given list of strings as a body of text, with each line indented to the left
     * and right by 2 spaces, and wrapped to fit within the interface width.
     * 
     * @param lines The list of strings to format as a body of text.
     * @return The formatted string.
     */
    public static String formatAsBody(List<String> lines) {
        return PageBuilder.formatAsBody(lines, 4, 2);
    }

    /**
     * Formats a given list of strings as a body of text, with each line indented to the left
     * and right by the specified number of spaces, and wrapped to fit within the specified total width.
     * 
     * @param lines The list of strings to format as a body of text.
     * @param padLeft The number of spaces to indent to the left of each line.
     * @param padRight The number of spaces to indent to the right of each line.
     * @param totalWidth The total width that the formatted text should fit within.
     * @return The formatted string.
     */
    public static String formatAsBody(List<String> lines, int padLeft, int padRight) {
        int textMaxWidth = Config.INTERFACE_WIDTH - padLeft - padRight;
        return PageBuilder.padLines(splitWords(lines, textMaxWidth), padLeft, padRight, Config.INTERFACE_WIDTH);
    }

    /**
     * Pads a given string of lines with spaces to the left and right, and wraps the text to fit within the specified total width.
     * 
     * @param lines The string of lines to pad.
     * @param left The number of spaces to indent to the left of each line.
     * @param right The number of spaces to indent to the right of each line.
     * @param totalWidth The total width that the formatted text should fit within.
     * @return The formatted string.
     */
    public static String padLines(String lines, int left, int right, int totalWidth) {
        return (PageBuilder.padLines(Arrays.asList(lines.split("\n")), left, right, totalWidth));
    }
    
    /**
     * Pads a given list of strings with spaces to the left and right, and wraps the text to fit within the specified total width.
     * 
     * @param lines The list of strings to pad.
     * @param left The number of spaces to indent to the left of each line.
     * @param right The number of spaces to indent to the right of each line.
     * @param totalWidth The total width that the formatted text should fit within.
     * @return The formatted string.
     */
    public static String padLines(List<String> lines, int left, int right, int totalWidth) {

        int maxTextLength = Math.max(totalWidth - left - right, 0);

        StringBuilder result = new StringBuilder();
        String padLeft = " ".repeat(left);
        String padRight = " ".repeat(right);
            
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            result.append(padLeft);
            result.append(
                line.length() > maxTextLength
                    ? line.substring(0, maxTextLength) 
                    : line + " ".repeat(Math.max(maxTextLength - line.length(), 0))
            );
            result.append(padRight);
            if (i < lines.size() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }


    /**
     * Splits a given string into individual words, and then reassembles them into lines of text
     * that do not exceed the given limit in length. The original string is split into lines using the
     * newline character, and then each line is split into individual words using whitespace as a delimiter.
     * The resulting list of strings contains the reassembled lines of text.
     * 
     * @param input The string to split into lines and words.
     * @param limit The maximum length of each line in the resulting list of strings.
     * @return A list of strings containing the reassembled lines of text.
     */
    public static List<String> splitWords(String input, int limit) {
        return PageBuilder.splitWords(Arrays.asList(input.split("\n")), limit);
    }

    /**
     * Splits a given list of strings into individual words, and then reassembles them into lines of text
     * that do not exceed the given limit in length. The original list of strings is split into lines using the
     * newline character, and then each line is split into individual words using whitespace as a delimiter.
     * The resulting list of strings contains the reassembled lines of text.
     * 
     * @param input The list of strings to split into lines and words.
     * @param limit The maximum length of each line in the resulting list of strings.
     * @return A list of strings containing the reassembled lines of text.
     */
    public static List<String> splitWords(List<String> input, int limit) {
        List<String> lines = new ArrayList<>();
        if (input.isEmpty()) {
            return lines;
        }

        for (String rawLine : input) {

            int indentSize = 0;
            while (indentSize < rawLine.length() && rawLine.charAt(indentSize) == ' ') {
                indentSize++;
            }
            String indent = " ".repeat(indentSize);

            String content = rawLine.substring(indentSize).strip();
            if (content.isEmpty()) {
                lines.add(indent);
                continue;
            }

            StringBuilder current = new StringBuilder(indent);
            for (String word : content.split(" +")) {

                if (current.length() + word.length() + 1 > limit) {
                    lines.add(current.toString());
                    current = new StringBuilder();
                }

                if (current.length() > indentSize) {
                    current.append(' ');
                }
                current.append(word);
            }

            lines.add(current.toString());
        }

        return lines;
    }

    /**
     * Formats a given string to be centered within the interface width.
     * The string is padded with spaces to the left and right to center it.
     * The amount of padding to the left and right is specified by the padLeft and padRight parameters.
     * If the string is already longer than the interface width, it is left unchanged.
     * 
     * @param str The string to center.
     * @param padLeft The number of spaces to pad to the left of the string.
     * @param padRight The number of spaces to pad to the right of the string.
     * @return The formatted string.
     */
    public static String formatBodyToCenter(String str) {
        return formatBodyToCenter(str, 4, 2);
    }

    /**
     * Formats a given string to be centered within the interface width, with a given number of spaces to pad to the left and right.
     * The string is padded with spaces to the left and right to center it.
     * If the string is already longer than the interface width, it is left unchanged.
     * 
     * @param str The string to center.
     * @param padLeft The number of spaces to pad to the left of the string.
     * @param padRight The number of spaces to pad to the right of the string.
     * @return The formatted string.
     */
    public static String formatBodyToCenter(String str, int padLeft, int padRight) {
        int textMaxWidth = Config.INTERFACE_WIDTH - padLeft - padRight;
        return PageBuilder.formatStringToCenter(str, textMaxWidth);
    }

    /**
     * Centers a given string within the interface width using the given padding.
     * If the string is already longer than the interface width, it is left unchanged.
     * 
     * @param str The string to center.
     * @param padding The string to use for padding.
     * @return The formatted string.
     */
    public static String formatStringToCenter(String str, String padding) {
        return PageBuilder.formatStringToCenter(str, Config.INTERFACE_WIDTH, padding);
    }

    /**
     * Centers a given string within the interface width.
     * If the string is already longer than the interface width, it is left unchanged.
     * The string is padded with spaces to the left and right to center it.
     * 
     * @param str The string to center.
     * @return The formatted string.
     */
    public static String formatStringToCenter(String str) {
        return PageBuilder.formatStringToCenter(str, Config.INTERFACE_WIDTH, " ");
    }

    /**
     * Centers a given string within a specified width.
     * If the string is already longer than the specified width, it is left unchanged.
     * The string is padded with spaces to the left and right to center it.
     * 
     * @param str The string to center.
     * @param width The width to center the string within.
     * @return The formatted string.
     */
    public static String formatStringToCenter(String str, int width) {
        return PageBuilder.formatStringToCenter(str, width, " ");
    }

    /**
     * Centers a given string within a specified width using a given padding.
     * If the string is already longer than the specified width, it is left unchanged.
     * The string is padded with the given padding to the left and right to center it.
     * 
     * @param str The string to center.
     * @param width The width to center the string within.
     * @param padding The string to use for padding.
     * @return The formatted string.
     */
    public static String formatStringToCenter(String str, int width, String padding) {
        if (str.length() >= width) { return str; }

        int totalPadding = width - str.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return padding.repeat(leftPadding) + str + padding.repeat(rightPadding);
    }

    /**
     * Clears the console screen.
     * On Windows, this uses the cls command in the Command Prompt.
     * On other operating systems, this uses ANSI escape codes to clear the screen and move the cursor to the top left corner.
     */
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    // Instance Methods
    
    /**
     * Clears the console screen and then displays the page.
     * 
     * The HUD (if any) is printed first, followed by the header (if any).
     * The title is then centered and printed.
     * The sub-title (if any) is then centered and printed.
     * The error message (if any) is then centered and printed.
     * The body of the page is then printed.
     * If the body is not blank, an empty line is added after it.
     * 
     * The options are then printed, with each option on a new line.
     * The options are padded with spaces to align them with the center of the interface.
     * 
     * If there are custom options, they are printed after the normal options.
     * 
     * The enter option is then printed (if any).
     * 
     * The prompt input history is then printed, with each item on a new line.
     * 
     * Finally, a footer is printed, consisting of a horizontal line of dashes.
     */
    public void display() {
        PageBuilder.clear();

        if (!this.hud.isEmpty()) {
            System.out.println("=".repeat(Config.INTERFACE_WIDTH));
            System.out.println(this.hud);
            System.out.println("=".repeat(Config.INTERFACE_WIDTH));
        }

        if (!this.header.isEmpty()) {
            System.out.println(this.header);
            System.out.println("-".repeat(Config.INTERFACE_WIDTH));
        }

        System.out.println(PageBuilder.formatStringToCenter("[" + this.title + "]"));

        if (!this.subTitle.isEmpty()) {
            System.out.println(PageBuilder.formatStringToCenter(this.subTitle));
        }
        
        if (!this.errorMessage.isEmpty()){
            System.out.println(PageBuilder.formatStringToCenter("## "+ this.errorMessage + " ##"));
        } else {
            System.out.println();
        }

        System.out.println(this.body);

        if (!this.body.isBlank()) {
            System.out.println();
        }

        String padLeft = " ".repeat(Config.INTERFACE_WIDTH/10);

        for (int i = 0; i < this.options.size(); i++) {
            System.out.println(String.format("%s[%d] %s", padLeft, i + 1, this.options.get(i).getLabel()));
        }
        
        for (String displayOption : this.displayOptions) {
            System.out.println(padLeft + displayOption);
        }

        if ((!this.options.isEmpty() || !this.displayOptions.isEmpty()) && !this.customOptions.isEmpty()) {
            System.out.println();
        }

        for (CustomOption customOption : this.customOptions) {
            System.out.println(String.format("%s[%s] %s", padLeft, customOption.getToken(), customOption.getLabel()));
        }

        if (this.enterOption != null) {
            System.out.println(String.format("%s[ENTER] %s", padLeft, this.enterOption.getLabel()));
        }

        System.out.println("-".repeat(Config.INTERFACE_WIDTH));

        for (String promptInput : this.promptInputHistory) {
            System.out.println(promptInput);
        }
        
        return;
    }

    /**
     * Loops until the user inputs a valid option and returns the corresponding PageResult.
     * If the user inputs an invalid option, the method will display an error message
     * and loop again until a valid option is entered.
     * 
     * @param prompt the prompt to display to the user
     * @return the PageResult corresponding with the user's valid input
     */
    public PageResult nextOptionResultInputLoop(String prompt) {
        PageResult result = null;
        while (result == null) {
            try {
                this.display();
                result = this.nextOptionResult(prompt);
            } catch (InputMismatchException e) {
                this.setErrorMessage("Please enter a valid option!");
            }
        }
        return result;
    }

    /**
     * Loops until the user inputs a non-empty line and returns the corresponding PageResult.
     * If the user inputs an empty line, the method will display an error message
     * and loop again until a non-empty line is entered.
     * 
     * @param prompt the prompt to display to the user
     * @param emptyErrorMessage the error message to display if the user inputs an empty line
     * @return the PageResult corresponding with the user's non-empty line input
     */
    public PageResult.Str nextLineResultInputLoop(String prompt, String emptyErrorMessage) {
        PageResult.Str input;
        while (true) {
            this.display();
            input = this.nextLine(prompt);
            if (input.getPageResult() != null) {
                return input;
            }
            if (input.getValue().isEmpty()) {
                this.setErrorMessage(emptyErrorMessage);
                continue;
            }
            break;
        }
        return input;

    }

    /**
     * Loops until the user inputs a valid integer option within the given bounds
     * and returns the corresponding PageResult.
     * If the user inputs an integer outside of the bounds, the method will display an error message
     * and loop again until a valid option is entered.
     * If the user inputs an invalid option (e.g. non-integer), the method will display an error message
     * and loop again until a valid option is entered.
     * 
     * @param prompt the prompt to display to the user
     * @param min the minimum value (inclusive) that the user's input must be within
     * @param max the maximum value (inclusive) that the user's input must be within
     * @param outOfBoundsErrorMessage the error message to display if the user inputs an option outside of the bounds
     * @return the PageResult corresponding with the user's valid integer option input
     */
    public PageResult.Int nextIntResultInputLoop(String prompt, int min, int max, String outOfBoundsErrorMessage) {
        PageResult.Int input;

        while (true) {
            try {
                this.display();
                input = this.nextInt(prompt);

                if (input.getPageResult() != null)
                    return input;

                int value = input.getValue();
                if (value < min || value > max) {
                    this.setErrorMessage(outOfBoundsErrorMessage);
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                this.setErrorMessage("Please enter a valid option!");
            }
        }
        return input;
    }

    /**
     * Processes the given string input to determine which option was selected
     * by the user. If the string matches a custom option, the method will
     * return the corresponding PageResult. If the string matches a numbered option,
     * the method will return the corresponding PageResult. If the string matches
     * the enter option, the method will return the corresponding PageResult.
     * If the string does not match any of the above, the method will return null.
     * 
     * @param string the string input entered by the user
     * @return the PageResult corresponding with the user's input, or null if the input
     *        could not be resolved to a valid option
     */
    public PageResult processOptionResult(String string) {

        for (CustomOption customOption : this.customOptions) {
            if (string.equalsIgnoreCase(customOption.getToken())) {
                return customOption.getPageResult();
            }
        }

        for (int i = 0; i < this.options.size(); i++) {
            if (string.equals((Integer.toString(i + 1)))) {
                return this.options.get(i).getPageResult();
            }
        }

        if (this.enterOption != null) {
            return this.enterOption.getPageResult();
        }

        return null;
    }
    

    /**
     * Loops until the user inputs a valid option and returns the corresponding PageResult.
     * If the user inputs an invalid option, the method will display an error message
     * and loop again until a valid option is entered.
     * 
     * @param prompt the prompt to display to the user
     * @return the PageResult corresponding with the user's valid input
     * @throws InputMismatchException if the user inputs an invalid option
     */
    public PageResult nextOptionResult(String prompt) throws InputMismatchException {
        System.out.print(String.format("  >> %s: ", prompt));
        String input = PageBuilder.scanner.nextLine().strip();

        PageResult pageResult = this.processOptionResult(input);

        if (pageResult == null) {
            throw new InputMismatchException("Invalid option: '" + input + "' could not be resolved to a valid option.");
        }

        return pageResult;
    }

    /**
     * Loops until the user inputs a line of text and returns the corresponding PageResult.
     * If the user inputs an invalid option, the method will display an error message
     * and loop again until a valid option is entered.
     * 
     * @param prompt the prompt to display to the user
     * @return the PageResult corresponding with the user's valid input
     * @throws InputMismatchException if the user inputs an invalid option
     */
    public PageResult.Str nextLine(String prompt) {
        PageResult.Str result;

        System.out.print(String.format("  >> %s: ", prompt));
        String input = PageBuilder.scanner.nextLine().trim();

        PageResult pageResult = this.processOptionResult(input);

        if (pageResult == null) {
            result = new PageResult.Str(input, prompt);
        } else {
            result = new PageResult.Str(pageResult, prompt);
        }

        return result;
    }

    /**
     * Loops until the user inputs a valid integer and returns the corresponding PageResult.
     * If the user inputs an invalid option, the method will display an error message
     * and loop again until a valid integer is entered.
     * 
     * @param prompt the prompt to display to the user
     * @return the PageResult corresponding with the user's valid integer input
     * @throws InputMismatchException if the user inputs an invalid option
     */
    public PageResult.Int nextInt(String prompt) throws InputMismatchException {
        PageResult.Int result;

        System.out.print(String.format("  >> %s: ", prompt));
        String input = PageBuilder.scanner.nextLine().trim();

        PageResult pageResult = this.processOptionResult(input);

        if (pageResult == null) {
            try {
                result = new PageResult.Int(Integer.parseInt(input), prompt);
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Invalid integer: '" + input + "' could not be resolved to a valid integer.");
            }
        } else {
            result = new PageResult.Int(pageResult, prompt);
        }

        return result;
    }

}
