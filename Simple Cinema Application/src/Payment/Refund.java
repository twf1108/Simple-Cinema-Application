package Payment;

import java.util.Scanner;

import Booking.Ticket;

public class Refund {

    public static void processRefund(Ticket ticket, Voucher usedVoucher) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Refund Request ===");

        double movieTotal = (ticket.getMovie().getPrice().getChildPrice() * ticket.getSeat().getTypes()[0])
                + (ticket.getMovie().getPrice().getAdultPrice() * ticket.getSeat().getTypes()[1])
                + (ticket.getMovie().getPrice().getSeniorPrice() * ticket.getSeat().getTypes()[2]);

        double foodTotal = ticket.getFoods().calculatePrice(ticket.getFoods().getFoods());

        double totalAmount = movieTotal + foodTotal;
        double discount = (usedVoucher != null) ? usedVoucher.getDiscountAmount() : 0;
        double refundAmount = totalAmount - discount;

        System.out.printf("Original Total Paid: RM %.2f\n", refundAmount);

        System.out.println("\nSelect refund method (same as payment method):");
        System.out.println("1. Debit/Credit Card");
        System.out.println("2. Online Banking");
        System.out.println("3. E-Wallet");

        int method = 0;
        while (method < 1 || method > 3) {
            System.out.print("Enter choice (1-3): ");
            try {
                method = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }
        }

        switch (method) {
            case 1:
                System.out.println("\nProcessing refund to Debit/Credit Card...");
                simulateCardRefund(scanner, refundAmount);
                break;
            case 2:
                System.out.println("\nProcessing refund via Online Banking...");
                simulateOnlineBankingRefund(scanner, refundAmount);
                break;
            case 3:
                System.out.println("\nProcessing refund to E-Wallet...");
                simulateEWalletRefund(scanner, refundAmount);
                break;
        }

        if (usedVoucher != null) {
            System.out.println("\nReturning used voucher: " + usedVoucher.getCode());
            // Optionally, you could re-save the voucher back into voucher.txt if desired
            // Example:
            // List<Voucher> vouchers = VoucherManager.loadVouchers();
            // vouchers.add(usedVoucher);
            // VoucherManager.saveVouchers(vouchers);
        }

        System.out.println("\n[✔] Refund of RM " + String.format("%.2f", refundAmount) + " completed successfully.");
        System.out.print("Press Enter to return to menu...");
        scanner.nextLine();
    }

    private static void simulateCardRefund(Scanner scanner, double amount) {
        System.out.print("Enter last 4 digits of your card for verification: ");
        scanner.nextLine(); // skip input for now, simulate refund
        System.out.println("Refunding RM " + String.format("%.2f", amount) + " to your card...");
        delay();
    }

    private static void simulateOnlineBankingRefund(Scanner scanner, double amount) {
        System.out.print("Enter your bank login ID for verification: ");
        scanner.nextLine(); // skip input for now
        System.out.println("Refunding RM " + String.format("%.2f", amount) + " via Online Banking...");
        delay();
    }

    private static void simulateEWalletRefund(Scanner scanner, double amount) {
        System.out.print("Enter your E-Wallet ID: ");
        scanner.nextLine(); // skip input for now
        System.out.println("Refunding RM " + String.format("%.2f", amount) + " to E-Wallet...");
        delay();
    }

    private static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}