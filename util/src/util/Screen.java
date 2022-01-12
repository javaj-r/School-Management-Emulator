package util;

import java.io.Console;
import java.util.Map;
import java.util.Scanner;

public class Screen {

    private static final Scanner SCANNER;
    private static final Console CONSOLE;
    public static final boolean IS_WINDOWS;
    public static final String USER_NAME;
    public static final String PC_NAME;

    static {
        SCANNER = new Scanner(System.in);
        CONSOLE = System.console();
        IS_WINDOWS = System.getProperty("os.name").contains("Windows");
        USER_NAME = System.getProperty("user.name");
        PC_NAME = getComputerName();
    }

    private Screen() {
        throw new IllegalStateException("Utility class");
    }

    private static String getComputerName()
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown-Computer");
    }


    public static String getString(String message) {
        System.out.print(message);
        return SCANNER.nextLine();
    }

    public static String getPassword(String message) {
        System.out.print(message);
        return CONSOLE == null ? new Scanner(System.in).nextLine()
                : String.valueOf(CONSOLE.readPassword());
    }

}
