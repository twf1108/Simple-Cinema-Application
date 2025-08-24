package Payment;

import java.util.Scanner;
import java.time.LocalDate;

public class DebitCreditCardPayment implements PaymentMethod {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String FIXED_CARD_NUMBER = "1234567812345678";
    private static final String FIXED_CVV = "123";
    private static final String FIXED_PASSWORD = "123456";

    @Override
    public void pay(double amount) {
        System.out.println("\nProcessing Debit/Credit Card Payment...");

        String cardNumber = getCardNumber();
        String expiryDate = getExpiryDate(cardNumber);  // Card number validation followed by expiry date check
        String cvv = getCVV();
        String password = getPassword();

        if (validateCard(cardNumber, expiryDate, cvv, password)) {
            System.out.printf("Paid RM %.2f using Debit/Credit Card.\n", amount);
        } else {
            System.out.println("[✘] Invalid card details or expired card. Payment failed.");
        }
    }

    private String getCardNumber() {
        String cardNumber;
        while (true) {
            System.out.print("Enter your 16-digit Card Number: ");
            cardNumber = scanner.nextLine().trim();
            if (cardNumber.equals(FIXED_CARD_NUMBER)) {
                break;
            } else {
                System.out.println("[✘] Invalid card number. Please try again.");
            }
        }
        return cardNumber;
    }

    private String getExpiryDate(String cardNumber) {
        String expiryDate;
        while (true) {
            System.out.print("Enter Expiry Date (MM/YY): ");
            expiryDate = scanner.nextLine().trim();
            if (expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
                String month = expiryDate.substring(0, 2);
                String year = expiryDate.substring(3, 5);
                int monthInt = Integer.parseInt(month);
                int yearInt = Integer.parseInt(year);

                LocalDate currentDate = LocalDate.now();
                LocalDate expiry = LocalDate.of(2000 + yearInt, monthInt, 1);

                if (expiry.isAfter(currentDate)) {
                    break;
                } else {
                    System.out.println("[✘] The card has expired. Please re-enter the card number and expiry date.");
                    getCardNumber();  // Prompt for card number again if expired
                    continue;  // Retry expiry date input
                }
            } else {
                System.out.println("[✘] Invalid expiry date format. Please enter in MM/YY format.");
            }
        }
        return expiryDate;
    }

    private String getCVV() {
        String cvv;
        while (true) {
            System.out.print("Enter CVV (3 digits): ");
            cvv = scanner.nextLine().trim();
            if (cvv.equals(FIXED_CVV)) {
                break;
            } else {
                System.out.println("[✘] Invalid CVV. Please try again.");
            }
        }
        return cvv;
    }

    private String getPassword() {
        String password;
        while (true) {
            System.out.print("Enter your 6-digit Card Password: ");
            password = scanner.nextLine().trim();
            if (password.equals(FIXED_PASSWORD)) {
                break;
            } else {
                System.out.println("[✘] Invalid password. Please try again.");
            }
        }
        return password;
    }

    private boolean validateCard(String cardNumber, String expiryDate, String cvv, String password) {
        return cardNumber.equals(FIXED_CARD_NUMBER) &&
               expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$") &&
               cvv.equals(FIXED_CVV) &&
               password.equals(FIXED_PASSWORD);
    }
}
