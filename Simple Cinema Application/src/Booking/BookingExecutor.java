package Booking;

import java.util.ArrayList;
import java.util.Scanner;

import Module.Clear_Screen;
import Module.FileHandle;
import Module.IAndOUtil;
import Movies.Movie;
import Movies.MovieShowtimeFile;
import Payment.Voucher;

public class BookingExecutor {
    private Ticket ticket;
    private boolean reset = false;
    private int selection = 0;
    private ArrayList<String> movies = clearString(MovieShowtimeFile.movieList());
    private Voucher appliedVoucher = null;

    public Movie begin(int selection, Scanner scanner) {
        String prompt = "Please select the movie you want (0 for return): ";
        String title = "\n== Movie Menu ==";
        IAndOUtil.displayMenu(title, this.movies.toArray(String[]::new));
        selection = IAndOUtil.readInt(scanner, prompt, 0 , movies.size());
        if (selection != 0) return FileHandle.readMovie(movies, selection);
        else return null;
    }

    private ArrayList<String> clearString(ArrayList<String> movies) {
        ArrayList<String> buffer = new ArrayList<>();
        for (String movie : movies) {
            buffer.add(movie.replace(".txt", ""));
        }
        return buffer;
    }

    public boolean main(String username) {
        this.ticket = new Ticket();
        Scanner scanner = new Scanner(System.in);

        do {
            Clear_Screen.clearScreen();
            ticket.setMovie(begin(this.selection, scanner));
            if (ticket.getMovie() != null) {
                System.out.println(ticket.getMovie().toString());

                this.reset = new Date().showDates(FileHandle.readDate(this.ticket));

                if (!this.reset) {
                    String prompt = "Please enter the position of date (0 for return movie menu): ";
                    this.selection = IAndOUtil.readInt(scanner, prompt, 0, 7);

                    if (this.selection != 0) {
                        this.ticket.setDate(FileHandle.readDate(this.ticket).get(this.selection - 1));
                        new Time().displayValidTimes(FileHandle.readTime(this.ticket));

                        prompt = "Please enter the position of time (0 for return movie menu): ";
                        this.selection = IAndOUtil.readInt(scanner, prompt, 0, FileHandle.readTime(this.ticket).size());

                        if (this.selection != 0) {
                            this.ticket.setTime(FileHandle.readTime(this.ticket).get(this.selection - 1));
                            this.ticket.setHall(FileHandle.readHall(this.ticket));

                            if (this.ticket.getHall().getHallNo().length() > 3) {
                                System.out.println(this.ticket.getHall().getHallNo());
                                break;
                            } else {
                                this.ticket.setSeat(new SeatBuffer().executor(scanner, this.ticket));
                                this.ticket.displayCurrentStatus();
                                this.ticket.setFoods(new FoodExecutor().orderFood(scanner));
                                this.ticket.finalTicket();

                                appliedVoucher = chooseVoucher(scanner);
                                Payment.Payment.processPayment(this.ticket, appliedVoucher, username);
                            }
                        } else this.reset = true;
                    } else this.reset = true;
                }
            } else return this.reset = true;
        } while (this.reset);

        return this.reset;
    }

    private Voucher chooseVoucher(Scanner scanner) {
        System.out.println("\n\n== Voucher Options ==");

        
        double ticketTotal = (ticket.getMovie().getPrice().getChildPrice() * ticket.getSeat().getTypes()[0]) +
                             (ticket.getMovie().getPrice().getAdultPrice() * ticket.getSeat().getTypes()[1]) +
                             (ticket.getMovie().getPrice().getSeniorPrice() * ticket.getSeat().getTypes()[2]);

        // If the total ticket price is less than RM 30, disable voucher option
        if (ticketTotal < 30.0) {
            System.out.println("The ticket price is RM " + ticketTotal + ". No voucher can be applied.");
            return null;
        }

        System.out.println("1. Check Voucher");
        System.out.println("2. No Voucher");
        System.out.print("Please select an option: ");
        int voucherChoice = IAndOUtil.readInt(scanner, "", 1, 2);

        if (voucherChoice == 1) {
            System.out.println("\n== Available Vouchers ==");

            // Display Movie Voucher
            System.out.println("1. Movie Voucher - RM 10");

            // Check if any food or drink has been purchased before allowing the F&B voucher
            if (ticket.getFoods() != null && !ticket.getFoods().getFoods().isEmpty()) {
                System.out.println("2. Food & Beverage Voucher - RM 5");
            } else {
                System.out.println("Food & Beverage Voucher is not available since no food or drink is selected.");
            }

            System.out.print("Select a voucher: ");
            int voucherSelection = IAndOUtil.readInt(scanner, "", 1, (ticket.getFoods() != null && !ticket.getFoods().getFoods().isEmpty()) ? 2 : 1);

            if (voucherSelection == 1) {
                return new Voucher("Movie", 10.0);
            } else if (voucherSelection == 2 && ticket.getFoods() != null && !ticket.getFoods().getFoods().isEmpty()) {
                return new Voucher("F&B", 5.0);
            } else {
                System.out.println("Invalid selection for F&B Voucher. Please select a valid option.");
                return null; 
            }
        }
        return null; 
    }



    public Ticket getTicket() {
        return this.ticket;
    }

    public Voucher getUsedVoucher() {
        return this.appliedVoucher;
    }
}
