package Login_Registration;

import UserData.Member_User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User_Validation {
    private static final String USER_FILE = "users.txt";

    public static boolean isUserExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 2) {
                    String fileUsername = userData[0].trim();
                    if (fileUsername.equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }

    public static boolean authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 2) {
                    String fileUsername = userData[0].trim();
                    String filePassword = userData[1].trim();
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }
        return false;
    }
}
