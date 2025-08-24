package UserData;

import java.util.Scanner;

import Booking.BookingExecutor;
import Module.*;
import Movies.*;
import Payment.Refund;
import Payment.Voucher;
import Booking.Ticket;
import java.util.ArrayList;

public class Member_User extends Base_User{
    private Ticket lastTicket = null;
    private Voucher usedVoucher = null;

    public Member_User(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Clear_Screen.clearScreen();
            Display.Member_Menu();
            String title = "Choose an option: ";
            int choice = IAndOUtil.readInt(scanner, title, 1, 5);
            ArrayList<String> record;
            boolean reset = true;
            switch (choice) {
                case 1: // View Now Showing Movies
                    Clear_Screen.clearScreen();
                    MovieShowtimeFile.viewMovieList();
                    break;
                case 2: // Book Ticket
                    Clear_Screen.clearScreen();
                    BookingExecutor executor = new BookingExecutor();
                    boolean success = executor.main(this.username);

                    if (success) {
                        this.lastTicket = executor.getTicket();
                        this.usedVoucher = executor.getUsedVoucher();
                    }
                    break;
                case 3:
                    Clear_Screen.clearScreen();
                    reset = true;
                    
                    while(reset){
                        record = FileHandle.viewRecord(this.username);
                        if (record.isEmpty()) {
                            System.out.println("[!] No booking records found.");
                            Pause.pause(scanner);
                            break;
                        }
                        else{                      
                            title = "\n == Your Record =="; 
                            IAndOUtil.displayMenu(title, record.toArray(new String[0]));

                            title = "Which you would like to refund insert the index(0 for return):";
                            int selection = IAndOUtil.readInt(scanner, title, 0, record.size());
                            title = "Are you confirm want to refund " + record.get(selection - 1) +" (Y/N):";
                            scanner.skip("\n");
                            char c = IAndOUtil.readChar(scanner, title, 'Y', 'N');
                            
                            if(c == 'Y'){
                                if(selection != 0){
                                    FileHandle.deleteTicket(record.get(selection - 1));
                                    scanner.skip("\n");
                                    title = "Refund other ticket record(Y/N):";
                                    char confirm = IAndOUtil.readChar(scanner, title, 'Y', 'N');
                                    reset = confirm == 'N' ? false : true;
                                } else reset = false;
                            }
                        }
                    }
                    
                    break;

                case 4:
                    reset = true;
                    
                    while(reset){
                        record = FileHandle.viewRecord(this.username);
                        if (record.isEmpty()) {
                            System.out.println("[!] No booking records found.");
                            Pause.pause(scanner);
                            break;
                        }
                        else{                      
                            title = "\n == Your Record =="; 
                            IAndOUtil.displayMenu(title, record.toArray(new String[0]));

                            title = "Which record you would like to view(0 for return):";
                            int selection = IAndOUtil.readInt(scanner, title, 0, record.size());

                            if(selection != 0){
                                FileHandle.getSpecificRecord(record.get(selection - 1));
                                
                                scanner.skip("\n");
                                title = "View other ticket record(Y/N):";
                                char confirm = IAndOUtil.readChar(scanner, title, 'Y', 'N');
                                reset = confirm == 'N' ? false : true;
                            } else reset = false;
                        }
                    }
                    break;
                    
                default:                   
                    return;
            }
            Pause.pause(scanner);
        }
    }
}
