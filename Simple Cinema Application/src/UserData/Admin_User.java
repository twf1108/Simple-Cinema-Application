package UserData;

import java.util.Scanner;

import Admin.Admin_Run;
import Module.*;

public class Admin_User extends Base_User{
    public Admin_User(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Clear_Screen.clearScreen();
            Admin_Run.main(scanner);
            return;
        }
    }
}
