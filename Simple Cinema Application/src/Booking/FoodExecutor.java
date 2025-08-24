package Booking;

import java.util.*;
import Module.IAndOUtil;
import Payment.Voucher;

public class FoodExecutor extends Food {
    private ArrayList<Object> foods;

    public FoodExecutor(ArrayList<Object> foods) {
        this.foods = foods;
    }

    FoodExecutor() {}

    public ArrayList<Object> getFoods() {
        return this.foods;
    }

    @Override
    public double calculatePrice(ArrayList<Object> a) {
        double total = 0.0;
        for (Object item : a) {
            if (item instanceof Food) {
                Food f = (Food) item;
                total += f.getPrice();
            }
        }
        return total;
    }

    @Override
    public void displaySelected(ArrayList<Object> a) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) instanceof Food) {
                Food f = (Food) a.get(i);
                if (i != a.size() - 1) 
                    System.out.print(f.getName() + ", ");
                else 
                    System.out.print(f.getName());
            }
        }
    }

    @Override
    public Object returnObject(int selection, int sizeSelection) {
        if (selection <= 8) { 
            SolidFood sf = new SolidFood();
            return sf.returnObject(selection, sizeSelection);
        } else {
            Drink d = new Drink();
            return d.returnObject(selection - 8, sizeSelection);
        }
    }

    public FoodExecutor orderFood(Scanner scanner) {
        this.foods = new ArrayList<>();
        char confirm = 'N';
        String prompt;
        boolean reset = false;
        do {
            if (confirm == 'Y') {
                System.out.print("\n\n\u001B[32mCurrent food order: ");
                displaySelected(this.foods);
                System.out.println();
            }
            if(confirm == 'N') System.out.println("\n\n == Food Order ==");
            else System.out.println("== Food Order ==");
            System.out.println("1. Food");
            System.out.println("2. Drinks");
            prompt = "Select food type (1-2, 0 to cancel): ";
            int foodType = IAndOUtil.readInt(scanner, prompt, 0, 2);
            
            if (foodType == 0) break;
            
            int selection, sizeSelection;
            Object selectedItem = null;
            
            if (foodType == 1) {
                System.out.println("\n == Food ==");
                SolidFood temp = new SolidFood();
                for (int i = 0; i < temp.getFoods().length; i++) {
                    System.out.printf("%02d. %-20s (Price: RM %.2f)%n", i + 1, temp.getFoods()[i], temp.getFood_Prices()[i]);
                }
                prompt = "Select food (1-8, 0 for return): ";
                selection = IAndOUtil.readInt(scanner, prompt, 0, 8);
                
                if (selection == 0) {
                    reset = true;
                } else {
                    prompt = "Select size (1-Regular, 2-Large, 3-Combo): ";
                    sizeSelection = IAndOUtil.readInt(scanner, prompt, 1, 3);
                    selectedItem = temp.returnObject(selection, sizeSelection);
                }
            } else {
                System.out.println("\n == Drink ==");
                Drink temp = new Drink();
                for (int i = 0; i < temp.getDrinks().length; i++) {
                    System.out.printf("%02d. %-20s (Price: RM %.2f)%n", i + 1, temp.getDrinks()[i], temp.getDrink_Prices()[i]);
                }
                prompt = "Select drink (1-8, 0 for return): ";
                selection = IAndOUtil.readInt(scanner, prompt, 0, 8);
                if (selection == 0) {
                    reset = true;
                } else {
                    prompt = "Select size (1-Small, 2-Medium, 3-Large): ";
                    sizeSelection = IAndOUtil.readInt(scanner, prompt, 1, 3);

                    selectedItem = temp.returnObject(selection, sizeSelection);                    
                }
            }
            
            if (selectedItem != null) {
                this.foods.add(selectedItem);
            }
            if (!reset) {
                scanner.nextLine();
                prompt = "Add more items (Y/N): ";
                confirm = IAndOUtil.readChar(scanner, prompt, 'Y', 'N');
                reset = confirm == 'Y';
            }
        } while (reset);
        return new FoodExecutor(this.foods);
    }

    public boolean isFoodEmpty() {
        return this.foods.isEmpty();
    }

    public double getTotalFoodPrice() {
        return calculatePrice(this.foods);
    }
    
    public static ArrayList<String> forSave(FoodExecutor foods){
        ArrayList<String> s = new ArrayList<>();
        for(Object object: foods.getFoods()){
            if(object instanceof Food){
                s.add(((Food) object).getName());
            }
        }
        return s;
    }
}
