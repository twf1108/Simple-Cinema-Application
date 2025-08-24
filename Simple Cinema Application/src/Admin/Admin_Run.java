package Admin;

import java.util.*;

import Module.Clear_Screen;
import Module.Display;
import Module.Pause;

public class Admin_Run {
    public static void main(Scanner scanner) {
        while (true) {
            Clear_Screen.clearScreen();
            Display.Admin_Menu();

            int choice;
            while (true) {
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException ex) {
                    System.out.print("Please enter a valid choice!");
                }
            }
            
            switch (choice) {
                case 1:
                    Clear_Screen.clearScreen();
                    MovieModify.viewMovie(scanner);
                    break;
                case 2:
                    Clear_Screen.clearScreen();
                    ShowtimeModify.viewShowtime(scanner);
                    break;
                case 3:
                    Clear_Screen.clearScreen();
                    MovieModify.addMovie(scanner);
                    break;
                case 4:
                    Clear_Screen.clearScreen();
                    MovieModify.modifyMovie(scanner);
                    break;
                case 5:
                    Clear_Screen.clearScreen();
                    MovieModify.delMovie(scanner);
                    break;
                case 6:
                    Clear_Screen.clearScreen();
                    ShowtimeModify.addShowtime(scanner);
                    break;
                case 7: 
                    Clear_Screen.clearScreen();
                    ShowtimeModify.deleteShowtime(scanner);
                    break;
                case 8:
                    System.out.print("Are you sure you want to logout? (Y/N):");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Y")) {
                        return;
                    } else if (input.equalsIgnoreCase("N")) {
                        System.out.println("Logout canceled...");
                        break;
                    } else {
                        System.out.println("Please enter Y/N. ");
                    }
                    break;
                default:
                    System.out.println("Invalid option");
            }

            Pause.pause(scanner);
        }
    }
}
