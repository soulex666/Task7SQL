package ua.com.foxminded.task7sql.provider;

import java.util.Scanner;

public final class ConsoleReader {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleReader() {
    }

    public static String readLine() {
        return SCANNER.nextLine();
    }

    public static int readInt() {
        String s = SCANNER.nextLine();
        return Integer.parseInt(s);
    }
}
