package Payment;

import java.util.Scanner;

public class EWalletPayment implements PaymentMethod {

    private static final String FIXED_ID = "0123456789";
    private static final String FIXED_PIN = "1234";

    @Override
    public void pay(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nProcessing E-Wallet Payment...");

        String eWalletId = getEWalletId(scanner);
        String pin = getPin(scanner);

        if (eWalletId.equals(FIXED_ID) && pin.equals(FIXED_PIN)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.printf("Paid RM %.2f using E-Wallet.\n", amount);
        } else {
            System.out.println("[✘] Invalid ID or PIN. Please try again.");
        }
    }

    private String getEWalletId(Scanner scanner) {
        String id;
        while (true) {
            System.out.print("Enter your E-Wallet ID (Phone number): ");
            id = scanner.nextLine().trim();
            if (id.equals(FIXED_ID)) {
                break;
            } else {
                System.out.println("[✘] Invalid E-Wallet ID. Please try again.");
            }
        }
        return id;
    }

    private String getPin(Scanner scanner) {
        String pin;
        while (true) {
            System.out.print("Enter your E-Wallet PIN: ");
            pin = scanner.nextLine().trim();
            if (pin.equals(FIXED_PIN)) {
                break;
            } else {
                System.out.println("[✘] Incorrect PIN. Please try again.");
            }
        }
        return pin;
    }
}
