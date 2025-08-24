package Payment;

import java.util.List;
import java.util.Scanner;

public class VoucherService {
    
    private boolean hasFoodOrDrink(List<String> purchasedItems) {
        for (String item : purchasedItems) {
            if (item.toLowerCase().contains("food") || item.toLowerCase().contains("f&b") || item.toLowerCase().contains("drink")) {
                return true;
            }
        }
        return false;
    }

    public Voucher promptUserForVoucher(Scanner scanner, List<String> purchasedItems) {
        System.out.println("Do you want to redeem a voucher? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes") || response.equals("y")) {
            List<Voucher> vouchers = VoucherManager.loadVouchers();
            if (vouchers.isEmpty()) {
                System.out.println("No vouchers available.");
                return null;
            }

            System.out.println("Available Vouchers:");
            for (int i = 0; i < vouchers.size(); i++) {
                Voucher voucher = vouchers.get(i);
                
                // If no food or drink purchased, do not show F&B voucher
                if (voucher.getType().equalsIgnoreCase("F&B") && !hasFoodOrDrink(purchasedItems)) {
                    continue;
                }
                
                System.out.println((i + 1) + ". " + voucher.getType() + " - RM " + voucher.getDiscountAmount() + " (Code: " + voucher.getCode() + ")");
            }

            System.out.print("Enter the number of the voucher to redeem (0 for no voucher): ");
            int voucherSelection = Integer.parseInt(scanner.nextLine());

            if (voucherSelection > 0 && voucherSelection <= vouchers.size()) {
                Voucher selectedVoucher = vouchers.get(voucherSelection - 1);
                
                // If F&B voucher was selected but no food or drink is purchased, reject it
                if (selectedVoucher.getType().equalsIgnoreCase("F&B") && !hasFoodOrDrink(purchasedItems)) {
                    System.out.println("You cannot use the Food & Beverage voucher as no food or drink was purchased.");
                    return null;
                }
                
                System.out.println("Voucher applied: " + selectedVoucher.getCode());
                return selectedVoucher;
            } else {
                System.out.println("No voucher applied.");
                return null;
            }
        } else {
            System.out.println("No voucher applied.");
            return null;
        }
    }
}
