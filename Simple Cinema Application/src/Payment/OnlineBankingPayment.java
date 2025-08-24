package Payment;

import java.util.Scanner;
import java.util.regex.Pattern;

public class OnlineBankingPayment implements PaymentMethod {

    private static final String FIXED_LOGIN_ID = "0123456789";
    private static final String FIXED_PASSWORD = "123456";
    private static final String FIXED_OTP = "123456";  // 固定的 OTP
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void pay(double amount) {
        System.out.println("\nProcessing Online Banking Payment...");

        while (true) {
            String bank = selectBank();
            System.out.println("You selected: " + bank);

            String loginId = promptLoginId();
            if (loginId.equals(FIXED_LOGIN_ID)) {
                String password = promptPassword();
                if (password.equals(FIXED_PASSWORD)) {
                    String otp = promptOTP();
                    if (otp.equals(FIXED_OTP)) {
                        System.out.printf("Paid RM %.2f using Online Banking (%s).\n", amount, bank);
                        break;
                    } else {
                        System.out.println("[✘] Invalid OTP. Restarting payment process...");
                    }
                } else {
                    System.out.println("[✘] Invalid password. Please try again.");
                }
            } else {
                System.out.println("[✘] Invalid login ID. Please try again.");
            }
        }
    }

    private String selectBank() {
        String[] banks = { "Maybank", "Public Bank", "Ambank", "Standard Chartered" };
        int choice = -1;

        while (true) {
            System.out.println("\nSelect Your Bank:");
            for (int i = 0; i < banks.length; i++) {
                System.out.printf("%d. %s\n", i + 1, banks[i]);
            }
            System.out.print("Enter your choice (1-4): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 4) {
                    return banks[choice - 1];
                }
            } catch (NumberFormatException e) {}
            System.out.println("[✘] Invalid choice. Please enter a number between 1 and 4.");
        }
    }

    private String promptLoginId() {
        System.out.print("Enter your Online Banking Login ID: ");
        return scanner.nextLine().trim();
    }

    private String promptPassword() {
        System.out.print("Enter your Online Banking password: ");
        return scanner.nextLine().trim();
    }

    private String promptOTP() {
        System.out.print("Enter the 6-digit OTP sent to your phone: ");
        return scanner.nextLine().trim();
    }

    private boolean isValidOTP(String otp) {
        return Pattern.matches("^\\d{6}$", otp);
    }
}
