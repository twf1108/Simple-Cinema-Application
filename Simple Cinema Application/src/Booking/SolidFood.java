/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class SolidFood extends Food{
    private String portionSize;
    private boolean isSpicy;   
    private boolean isVegetarian; 
    private final String[] FOODS = {
    "Popcorn", "Hot Dog", "Nachos", "Chicken Nuggets",
    "French Fries", "Pizza Slice", "Pretzels", "Cheesy Wedges"
    };
    
    private final double[] FOOD_PRICES = {
    8.00, 9.50, 8.50, 10.00,
    6.50, 12.00, 7.00, 9.00
    };    
    public SolidFood(){}
    public SolidFood(String id, String name, double price, String portionSize, boolean isSpicy, boolean isVegetarian){
        super(id, name, price);
        this.portionSize = portionSize;
        this.isSpicy = isSpicy;
        this.isVegetarian = isVegetarian;
    }
    

    public String[] getFoods(){
        return this.FOODS;
    }
    public double[] getFood_Prices(){
        return this.FOOD_PRICES;
    }
    
    @Override 
    public double calculatePrice(ArrayList<Object> a){  
        double total = 0.0;
        
        for(Object food : a){
            if (food instanceof SolidFood) {
                SolidFood f = (SolidFood) food;
                total += f.getPrice(); 
            }
        }
        return total;
    }
    
    @Override 
    public void displaySelected(ArrayList<Object> a){
        for(int i = 0; i < a.size(); i++){
            if (a.get(i) instanceof SolidFood) {
                SolidFood f = (SolidFood) a.get(i);
                if(i != a.size() - 1) System.out.print(f.getName() + ", ");
                else System.out.print(f.getName());
            }
        }
    }
    
    @Override
    public Object returnObject(int selection, int sizeSelection) {
        String[] size = {"Regular", "Large", "Combo"};

        if (selection < 1 || selection > FOODS.length || sizeSelection < 1 || sizeSelection > size.length) {
            System.out.println("Invalid selection.");
            return null;
        }

        return new SolidFood("F000" + selection, FOODS[selection - 1], FOOD_PRICES[selection - 1],                 
            size[sizeSelection - 1], selection % 2 == 0, selection % 3 == 0);
    }

}
