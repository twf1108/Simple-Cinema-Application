package Payment;

import java.util.Scanner;

import Booking.Ticket;

public class Payment {
    public static void processPayment(Ticket ticket, Voucher appliedVoucher, String username) {
        double totalAmount = (ticket.getMovie().getPrice().getChildPrice() * ticket.getSeat().getTypes()[0]) +
                              (ticket.getMovie().getPrice().getAdultPrice() * ticket.getSeat().getTypes()[1]) +
                              (ticket.getMovie().getPrice().getSeniorPrice() * ticket.getSeat().getTypes()[2]) +
                              ticket.getFoods().calculatePrice(ticket.getFoods().getFoods());

        double discountAmount = (appliedVoucher != null) ? appliedVoucher.getDiscountAmount() : 0;
        double amountToPay = totalAmount - discountAmount;

        System.out.printf("\n\nCurrent total(RM):%.1f\n", totalAmount);
        System.out.printf("Amount to Pay: RM %.2f\n", amountToPay);

        System.out.println("\n\n== Select Payment Method: ==");
        System.out.println("1. Debit/Credit Card");
        System.out.println("2. Online Banking");
        System.out.println("3. E-Wallet");

        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.print("Enter your choice (1-3): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) break;
            } catch (NumberFormatException e) {
            }
            System.out.println("Invalid input. Please select 1, 2, or 3.");
        }

        PaymentMethod method = null;
        if (choice == 1) {
            method = new DebitCreditCardPayment();
        } else if (choice == 2) {
            method = new OnlineBankingPayment();
        } else if (choice == 3) {
            method = new EWalletPayment();
        }

        if (method != null) method.pay(amountToPay);

        Receipt receipt = new Receipt(ticket, appliedVoucher);
        receipt.printReceipt(username);
    }

    public static void processPayment(Ticket ticket, String username) {
        processPayment(ticket, null, username);
    }
}
