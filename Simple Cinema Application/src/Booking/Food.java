package Booking;

import java.util.ArrayList;
import java.util.Scanner;
import Module.IAndOUtil;

public abstract class Food {
    private String id;
    private String name;
    private double price;
    
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public String getId(){
        return this.id;
    }
    public Food(){}
    public Food(String id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public abstract double calculatePrice(ArrayList<Object> a);
    public abstract void displaySelected(ArrayList<Object> a);
    public abstract Object returnObject(int selection, int sizeSelection);
}
