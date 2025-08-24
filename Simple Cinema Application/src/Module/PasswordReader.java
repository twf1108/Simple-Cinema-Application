package Module;

import java.io.Console;
import java.util.Scanner;

public class PasswordReader {
    public static String readPassword (Scanner scanner) {
        Console console = System.console();
        String password;

        if (console != null) {
            char[] passwordChars = console.readPassword("Enter password: ");
            password = new String(passwordChars);
        } else {
            System.out.print("Enter password (input visible): ");
            password = scanner.nextLine();
        }

        return password;
    }
}
