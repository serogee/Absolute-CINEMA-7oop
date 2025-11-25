package cinema.utils;

import java.util.Scanner;

public class ConsoleInput {
    private static Scanner scanner = new Scanner(System.in);

    private ConsoleInput() {}

    public static Scanner getScanner() {
        if (scanner == null) {
            throw new IllegalStateException("Scanner has already been closed.");
        }
        return ConsoleInput.scanner;
    }

    public static void closeScanner() {
        if (ConsoleInput.scanner != null) {
            ConsoleInput.scanner.close();
            ConsoleInput.scanner = null;
        }
    }
}
