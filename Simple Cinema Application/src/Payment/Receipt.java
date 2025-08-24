package Payment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import Booking.Ticket;
import Module.FileHandle;

public class Receipt {

    private Ticket ticket;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;
    private Voucher voucher;
    private Voucher bonusVoucher;

    public Receipt(Ticket ticket, Voucher voucher) {
        this.ticket = ticket;
        this.voucher = voucher;
        this.totalAmount = calculateTotal();
        double voucherDiscount = (voucher != null) ? voucher.getDiscountAmount() : 0;
        this.discountAmount = voucherDiscount;
        this.finalAmount = totalAmount - discountAmount;
        checkBonusVoucher();
    }

    private double calculateTotal() {
        double movieTotal = (ticket.getMovie().getPrice().getChildPrice() * ticket.getSeat().getTypes()[0])
                          + (ticket.getMovie().getPrice().getAdultPrice() * ticket.getSeat().getTypes()[1])
                          + (ticket.getMovie().getPrice().getSeniorPrice() * ticket.getSeat().getTypes()[2]);
        double foodTotal = ticket.getFoods().calculatePrice(ticket.getFoods().getFoods());
        return movieTotal + foodTotal;
    }

    private void checkBonusVoucher() {
        if (totalAmount >= 100.00) {
            Random rand = new Random();
            int randomChoice = rand.nextInt(2);
            bonusVoucher = (randomChoice == 0)
                ? new Voucher("F&B", 5.00)
                : new Voucher("Movie", 10.00);
        }
    }

    private String generateQRCode(int width, int height) {
        StringBuilder qr = new StringBuilder();
        Random rand = new Random();
        char codeChar = '•';

        for (int i = 0; i < width + 2; i++) {
            qr.append('·');
        }
        qr.append("\n");

        for (int i = 0; i < height; i++) {
            qr.append('·');
            for (int j = 0; j < width; j++) {
                boolean showDot = rand.nextInt(3) == 0;
                qr.append(showDot ? codeChar : ' ');
            }
            qr.append('·');
            qr.append("\n");
        }

        for (int i = 0; i < width + 2; i++) {
            qr.append('·');
        }
        qr.append("\n");

        return qr.toString();
    }

    private int getMaxTicketID() {
        int maxTicketID = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("TicketRecord.txt"))) {
            String line;
            // Skip the header line
            reader.readLine(); 

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String ticketId = fields[0];  // This should be the ticket ID
                try {
                    int ticketNumber = Integer.parseInt(ticketId.substring(1));  // Extract and parse the number
                    if (ticketNumber > maxTicketID) {
                        maxTicketID = ticketNumber;
                    }
                } catch (NumberFormatException e) {
                    // Handle the exception in case the ticket ID is invalid or improperly formatted
                    System.err.println("Invalid ticket ID format: " + ticketId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading TicketRecord.txt: " + e.getMessage());
        }
        return maxTicketID;
    }

    private String generateTicketID() {
        int maxTicketID = getMaxTicketID();
        int newTicketID = maxTicketID + 1;
        return "T" + String.format("%04d", newTicketID);  // Format as T0001, T0002, etc.
    }
    
    public void printReceipt(String username) {
        StringBuilder content = new StringBuilder();
        
        content.append("========================================\n");
        content.append("             Movie Ticket Receipt      \n");
        content.append("========================================\n");
        content.append(String.format(" Ticket ID: %-30s \n", generateTicketID())); // Include the ticket ID
        content.append(String.format(" Movie: %-30s \n", ticket.getMovie().getTitle()));
        content.append(String.format(" Date : %-30s \n", ticket.getDate().getDate()));
        content.append(String.format(" Time : %-30s \n", ticket.getTime().getTime()));
        content.append(String.format(" Hall : %-30s \n", ticket.getHall().getHallNo()));
        content.append("----------------------------------------\n");
        content.append(String.format(" Total Before Discount: RM %-6.2f \n", totalAmount));
        content.append(String.format(" Discount Applied     : RM %-6.2f \n", discountAmount));
        content.append(String.format(" Total Payable        : RM %-6.2f \n", finalAmount));
        
        if (bonusVoucher != null) {
            content.append(String.format(" Bonus Voucher: %-15s RM %-6.2f \n", bonusVoucher.getType(), bonusVoucher.getDiscountAmount()));
        }

        content.append("----------------------------------------\n");
        content.append("             Your QR Code              \n");
        content.append("========================================\n");
        content.append(generateQRCode(10, 4)); 
        content.append("========================================\n");
        content.append(" Thank you for your purchase!         \n");
        content.append("========================================\n");

        try (FileWriter writer = new FileWriter("receipt.txt")) {
            writer.write(content.toString());
            System.out.println("\n[✔] Receipt successfully written to receipt.txt.");
        } catch (IOException e) {
            System.out.println("[✘] Failed to write receipt to file.");
        }
        
        FileHandle fileHandle = new FileHandle("\n" + generateTicketID() + "," + ticket.toString());
        FileHandle fileHandle1 = new FileHandle("\n" + username + "," + generateTicketID());
        fileHandle1.writeIntoFile("UserTicket.txt");
        fileHandle.writeIntoFile("TicketRecord.txt");
        System.out.println(content.toString());
    }

    public double getFinalAmount() {
        return finalAmount;
    }
}
