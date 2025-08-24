package Payment;

import java.io.*;
import java.util.*;

public class VoucherManager {
    private static final String VOUCHER_FILE = "voucher.txt";

    public static List<Voucher> loadVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        
        File file = new File(VOUCHER_FILE);
        if (!file.exists()) {
            System.out.println("Voucher file not found. Please check the file path.");
            return vouchers;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(VOUCHER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String type = parts[0].trim();
                    try {
                        double amount = Double.parseDouble(parts[1].trim());
                        vouchers.add(new Voucher(type, amount));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid voucher amount in line: " + line);
                    }
                } else {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading vouchers from file: " + e.getMessage());
        }
        return vouchers;
    }

    public static void saveVouchers(List<Voucher> vouchers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VOUCHER_FILE))) {
            for (Voucher voucher : vouchers) {
                writer.write(voucher.getType() + "," + voucher.getDiscountAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing vouchers to file: " + e.getMessage());
        }
    }
}
