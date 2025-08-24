package Movies;

public class Price {
    private double childPrice;
    private double adultPrice;
    private double seniorPrice;

    public Price(double childPrice, double adultPrice, double seniorPrice) {
        this.childPrice = childPrice;
        this.adultPrice = adultPrice;
        this.seniorPrice = seniorPrice;
    }  

    public double getChildPrice() {
        return childPrice;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public double getSeniorPrice() {
        return seniorPrice;
    }
    
    public String toFileString() {
        return childPrice + "," + adultPrice + "," + seniorPrice + "\n";
    }
}
