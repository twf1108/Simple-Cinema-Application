package Module;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public  class IAndOUtil {
    
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max){
                    return input;
                }
            } catch (InputMismatchException e) {
//                System.out.println("Please enter integer else cause(" + e +") !!!");
                scanner.next();
            }
            if(min == 0){
                System.out.println("Invalid input! Enter an integer between " + (min+1) + "-" + max + "!!!");
            }
            else{
                System.out.println("Invalid input! Enter an integer between " + min + "-" + max + "!!!");
            }
        }
    } 
    
    public static char readChar(Scanner scanner, String prompt, char one, char two) {
        while (true) {            
            System.out.print(prompt);
            String inputLine = scanner.nextLine().trim(); 
            if (!inputLine.isEmpty()) {
                char input = inputLine.toUpperCase().charAt(0);
                if (input == one || input == two) {
                    return input;
                } else {
                    System.out.println("Invalid input! Enter " + one + " or " + two + "!!!");
                }
            } else {
                System.out.println("Input cannot be empty! Please try again.");
            }
        }
    }
    
    public static void displayMenu(String title, String[] menu){
        int position = 1;
        System.out.println(title);
        for (String mv : menu){
            System.out.println(position +". "+ mv);
            position++;
        }
    }
}