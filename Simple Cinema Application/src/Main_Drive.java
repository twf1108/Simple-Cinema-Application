// Welcome to XYZ Cinema //

import java.util.Scanner;

import Login_Registration.*;
import Module.*;

public class Main_Drive {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main Menu
        while (true) {
            Clear_Screen.clearScreen();
            Display.Main_Menu(); // Main Menu UI

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    Clear_Screen.clearScreen();
                    Login_Service.login(scanner);
                    break;
                case "2":
                    Clear_Screen.clearScreen();
                    Registration_Service.register(scanner);
                    break;
                case "3":
                    Clear_Screen.clearScreen();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    Pause.pause(scanner);
                    break;
            }
        }
    }
}

