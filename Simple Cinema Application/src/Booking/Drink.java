package Booking;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Drink extends Food{
    private String size;      
    private final String[] DRINKS = {
    "Soft Drink", "Mineral Water", "Milkshake", "Slushie",
    "Lemon Tea", "Iced Milo", "Sparkling Water", "Fruit Juice"
    };
    private final double[] DRINK_PRICES = {
        5.00, 4.00, 8.00, 6.50,
        5.50, 6.00, 5.50, 6.50
    };    
    
    public Drink(){};
    public Drink(String id, String name, double price, String size){
        super(id, name, price);
        this.size = size;
    }

    public String[] getDrinks(){
        return this.DRINKS;
    }
    public double[] getDrink_Prices(){
        return this.DRINK_PRICES;
    }
       
    @Override 
    public double calculatePrice(ArrayList<Object> a){  
        double total = 0.0;        
        for(Object drink : a){
            if (drink instanceof Drink) {
                Drink d = (Drink) drink;
                total += d.getPrice(); 
            }
        }
        // if(member == true) total /= 0.7;
        return total;
    }
    
    @Override 
    public void displaySelected(ArrayList<Object> a){
        for(int i = 0; i < a.size(); i++){
            if (a.get(i) instanceof Drink) {
                Drink d = (Drink) a.get(i);
                if(i != a.size() - 1) System.out.print(d.getName() + ", ");
                else System.out.print(d.getName());
            }
        }
    }
    
    @Override
    public Object returnObject(int selection, int sizeSelection) {
        String[] size = {"Small", "Medium", "Large"};

        if (selection < 1 || selection > DRINKS.length || sizeSelection < 1 || sizeSelection > size.length) {
            System.out.println("Invalid selection.");
            return null;
        }

        return new Drink("F000" + selection, DRINKS[selection - 1], DRINK_PRICES[selection - 1],                 
            size[sizeSelection - 1]);
    }
   
}
