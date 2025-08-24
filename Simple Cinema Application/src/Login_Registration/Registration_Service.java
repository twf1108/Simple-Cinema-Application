package Login_Registration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Module.*;

public class Registration_Service {
    private static final String USER_FILE = "users.txt";

    public static void register(Scanner scanner) {
        while (true) {
            Clear_Screen.clearScreen();
            Display.Registration_Page(); // Registration UI

            System.out.print("Enter a username (or type 'exit' to cancel): ");
            String username = scanner.nextLine().trim();

            if (username.isBlank()) {
                System.out.println("Username cannot be empty. Please try again...");
                Pause.pause(scanner);
                continue;
            }

            if (username.equalsIgnoreCase("exit")) {
                return;
            }

            if (username.equalsIgnoreCase("Admin")) {
                System.out.println("This username is reserved. Choose another username. ");
                Pause.pause(scanner);
                continue;
            }

            if (User_Validation.isUserExists(username)) {
                System.out.println("Username already taken. Choose another.");
                Pause.pause(scanner);
                continue;
            }

            System.out.print("Enter a password (or type 'exit' to cancel): ");
            String password = scanner.nextLine().trim();

            if (password.equalsIgnoreCase("exit")) {
                return;
            }

            if (password.isBlank()) {
                System.out.println("Password cannot be empty. Please try again...");
                Pause.pause(scanner);
                continue;
            }
            
            saveUser(username, password);
            Display.Registration_Successful(); // Registration Successful Message
            Pause.pause(scanner);
            return;
        }
    }
    
    private static void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username.trim() + "," + password.trim());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to user file.");
        }
    }
}
