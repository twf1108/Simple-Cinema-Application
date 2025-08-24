package Payment;

import java.util.Random;

public class Voucher {
    private String type;
    private double discountAmount;
    private String code;

    public Voucher(String type, double discountAmount) {
        this.type = type;
        this.discountAmount = discountAmount;
        this.code = generateVoucherCode(type);
    }

    private String generateVoucherCode(String type) {
        Random rand = new Random();
        int randomNum = 1000 + rand.nextInt(9000);
        if (type.equalsIgnoreCase("F&B")) {
            return "FNB" + randomNum;
        } else if (type.equalsIgnoreCase("Movie")) {
            return "MOV" + randomNum;
        } else {
            return "VOU" + randomNum;
        }
    }

    public String getType() {
        return type;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getCode() {
        return code;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isValidVoucher() {
        return true;
    }
}
