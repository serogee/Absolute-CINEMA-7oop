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
    public void addPromptInput(PageResult.DataType input) { 
        this.promptInputHistory.add("  >> " + input.getPrompt() + ": " + input.toString()); 
    }
    public void clearPromptInputHistory() { this.promptInputHistory.clear(); }

    // Utilities
    
    public static String formatAsBody(String line) {
        return PageBuilder.formatAsBody(Arrays.asList(line.split("\n")));
    }

    public static String formatAsBody(List<String> lines) {
        return PageBuilder.formatAsBody(lines, 4, 2);
    }

    public static String formatAsBody(List<String> lines, int padLeft, int padRight) {
        int textMaxWidth = Config.INTERFACE_WIDTH - padLeft - padRight;
        return PageBuilder.padLines(splitWords(lines, textMaxWidth), padLeft, padRight, Config.INTERFACE_WIDTH);
    }

    public static String padLines(String lines, int left, int right, int totalWidth) {
        return (PageBuilder.padLines(Arrays.asList(lines.split("\n")), left, right, totalWidth));
    }
    
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

    public static List<String> splitWords(String input, int limit) {
        return PageBuilder.splitWords(Arrays.asList(input.split("\n")), limit);
    }

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

    public static String formatBodyToCenter(String str) {
        return formatBodyToCenter(str, 4, 2);
    }

    public static String formatBodyToCenter(String str, int padLeft, int padRight) {
        int textMaxWidth = Config.INTERFACE_WIDTH - padLeft - padRight;
        return PageBuilder.formatStringToCenter(str, textMaxWidth);
    }

    public static String formatStringToCenter(String str, String padding) {
        return PageBuilder.formatStringToCenter(str, Config.INTERFACE_WIDTH, padding);
    }

    public static String formatStringToCenter(String str) {
        return PageBuilder.formatStringToCenter(str, Config.INTERFACE_WIDTH, " ");
    }

    public static String formatStringToCenter(String str, int width) {
        return PageBuilder.formatStringToCenter(str, width, " ");
    }

    public static String formatStringToCenter(String str, int width, String padding) {
        if (str.length() >= width) { return str; }

        int totalPadding = width - str.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return padding.repeat(leftPadding) + str + padding.repeat(rightPadding);
    }

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

    public PageResult.Char nextColumnInputLoop(String prompt, char min, char max, String outOfBoundsErrorMessage) {
        PageResult.Char input;

        while (true) {
            try {
                this.display();
                input = this.nextColumn(prompt);

                if (input.getPageResult() != null)
                    return input;
     
                char value = input.getValue();
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
            if (string.equalsIgnoreCase("ENTER") || string.isEmpty()) {
                return this.enterOption.getPageResult();
            }
        }

        return null;
    }
    

    public PageResult nextOptionResult(String prompt) throws InputMismatchException {
        System.out.print(String.format("  >> %s: ", prompt));
        String input = PageBuilder.scanner.nextLine().strip();

        PageResult pageResult = this.processOptionResult(input);

        if (pageResult == null) {
            throw new InputMismatchException("Invalid option: '" + input + "' could not be resolved to a valid option.");
        }

        return pageResult;
    }

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

    public PageResult.Char nextColumn(String prompt) throws InputMismatchException {
        PageResult.Char result;

        System.out.print(String.format("  >> %s: ", prompt));
        String input = PageBuilder.scanner.nextLine().trim();

        PageResult pageResult = this.processOptionResult(input);

        if (pageResult == null) {
            if (input.isEmpty()) {
                throw new InputMismatchException("Invalid character: '" + input + "' could not be resolved to a valid character.");
            }
            result = new PageResult.Char(Character.toUpperCase(input.charAt(0)), prompt);
        } else {
            result = new PageResult.Char(pageResult, prompt);
        }

        return result;
    }

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
