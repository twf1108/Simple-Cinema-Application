package Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Module.FileHandle;
import Module.IAndOUtil;

public class SeatBuffer{    
    private ArrayList<Seat> seats = new ArrayList<>();
    private int[] types = new int[3];
    
    public SeatBuffer(ArrayList<Seat> seats, int[] types){
        this.seats = seats;
        this.types = types;
    }
    public SeatBuffer(){}
    
    public ArrayList<Seat> getSeats(){
        return this.seats;
    }
    public int[] getTypes(){
        return this.types;
    }
    
    public static void displaySeat(ArrayList<Seat> holdSeat) {
        int seatNo = 1;
        System.out.println("\n == Valid Seat(\u001B[31mRed is unavailable seat\u001B[0m) ==");
        System.out.print("\u001B[32m\\\\                              Screen                                 \\\\\u001B[0m\n\n\n\n");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                String seatId = seatNo < 10 ? "S0" + seatNo : "S" + seatNo;
                boolean isHold = false;

                for(Seat seat : holdSeat) {
                    if(seat.getseat().equals(seatId)) {
                        isHold = true;
                        break;  
                    }
                }
                if(isHold) System.out.print("\u001B[31m" + seatId + "\u001B[0m\t");
                 else  System.out.print(seatId + "\t");  
                seatNo++;
            }
            System.out.println();
        }
    }

    public static ArrayList<Seat> readSeat(Scanner scanner, int quantity, ArrayList<Seat> seats) {
//        ArrayList<String> selectedSeats = new ArrayList<>();
        int remaining = quantity;
        ArrayList<Seat> selectedSeats = new ArrayList<>();
        
        scanner.skip("\n");
        while (remaining > 0) {
            if(!selectedSeats.isEmpty()){
                System.out.print("\u001B[32mCurrent seat(remaining:" + remaining + "):");
                for(int i = 0 ; i < selectedSeats.size(); i++){
                    Seat seat = selectedSeats.get(i);
                    if(i != selectedSeats.size()-1) System.out.print( seat.getseat()+", ");
                    else System.out.println( seat.getseat());
                }
            }
            System.out.print("Please enter seat number (e.g. S11): ");
            String input = scanner.nextLine().trim().toUpperCase();            
            try {
                Seat seat = new Seat(Seat.formatSeat(input));
                String validationError = validateSeat(seat, selectedSeats, seats);
                if (validationError != null) {
                    System.out.println("\u001B[31m" + validationError + "\u001B[0m");
                } else {
                    selectedSeats.add(seat);
                    remaining--;
                }
            } catch (Exception e) {
                System.out.println("\u001B[31mInvalid seat format! Please use SXX format.\u001B[0m");
            }
        }
        return selectedSeats;
    }

    private static String validateSeat(Seat seat, ArrayList<Seat> selected, List<Seat> occupied) {
        if (!seat.getseat().matches("S\\d{2}")) return "Invalid seat format!";
        if (SeatBuffer.contain(selected, seat)) return "Duplicate seat selection!";
        if (SeatBuffer.contain(occupied, seat)) return "Seat is unavailable!";
        return null;
    }
    private static boolean contain(ArrayList<Seat> selected, Seat seat){
        for(Seat selectSeat : selected){
            if(selectSeat.getseat().equals(seat.getseat())) return true;
        }
        return false;
    }
    private static boolean contain(List<Seat> occupied, Seat seat){
        for(Seat happen : occupied){
            if(happen.getseat().equals(seat.getseat())) return true;
        }
        return false;
    }
    
    public static int[] readType(Scanner scanner, int max, Ticket ticket){
        double[] price = {ticket.getMovie().getPrice().getChildPrice(), ticket.getMovie().getPrice().getAdultPrice()
                            ,ticket.getMovie().getPrice().getSeniorPrice()};
        int[] quantity = new int[3];
        String[] types = {"child", "adult", "senior"};
        System.out.println("\n == Type Selection ==");
        for(int i = 0; i < types.length; i++){
            System.out.println("\n\u001B[31mMax :" + max + "\u001B[0m");
            String prompt = "How many you want for " + types[i] + " (price :"+ price[i] + "):";
            max -= quantity[i] = IAndOUtil.readInt(scanner, prompt, 0, max);
        }
        return quantity;
    }
    
    public SeatBuffer executor(Scanner scanner, Ticket ticket){
        boolean reset = false;
        do{
            ArrayList<Seat> seats = FileHandle.readSeat(ticket, "TicketRecord.txt");   
            // ask seat quantity
            int max = (40 - seats.size());
            this.types = SeatBuffer.readType(scanner, max, ticket);
            int quantity = types[0] + types[1] + types[2];
            if(quantity != 0){
                // display seat 
                SeatBuffer.displaySeat(seats);  
                reset = false;
                return new SeatBuffer(readSeat(scanner, quantity, seats), this.types);
            }
            else{
                System.out.println("\nMust bust at least one ticket!");
                reset = true;
            }
        }while(reset);
        return null;
    }
    
    public void displaySelectedSeat(ArrayList<Seat> seats){
        for(Seat seat : seats)
            if(seats.indexOf(seat) == seats.size()-1) System.out.print(seat.getseat());
            else System.out.print(seat.getseat() + ", ");
    }
    
    public void displaySelectedTypes(SeatBuffer seats){
        for(int type = 0; type < seats.getTypes().length; type++){
            if(seats.getTypes().length - 1 == type) System.out.print(seats.getTypes()[type]);
            else System.out.print(seats.getTypes()[type] + ", ");
        }   
    }
}