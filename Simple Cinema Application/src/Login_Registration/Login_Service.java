package Login_Registration;

import java.util.Scanner;

import Module.*;
import UserData.*;

public class Login_Service {
    public static void login(Scanner scanner) {
        while (true) {
            Clear_Screen.clearScreen();
            Display.Login_Page(); // Login UI

            System.out.print("Enter username (or type 'exit' to cancel): ");
            String username = scanner.nextLine().trim();
            
            if (username.equalsIgnoreCase("exit")) {
                return;
            }

            if (!User_Validation.isUserExists(username)) {
                System.out.println("Username not found. Please try again. ");
                Pause.pause(scanner);
                continue;
            }

            String password = PasswordReader.readPassword(scanner).trim();

            if (User_Validation.authenticateUser(username, password)) {
                Base_User user;
                if (username.equals("Admin")) {
                    Display.Admin_Login(); // Admin Login Message
                    user = new Admin_User(username); // Going to User Data Admin
                } else {
                    Display.Login_Successful(); // Login Successful Message
                    user = new Member_User(username); // Going to User Data Member 
                }
                Pause.pause(scanner);
                user.showMenu();
            } else {
                System.out.println("Wrong password. Please try again. ");
                Pause.pause(scanner);
            }
        }
    }
}
