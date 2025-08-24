/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.util.ArrayList;

public class Seat{
    private String seat;
    
    public Seat(){}
    public Seat(String seat){
        this.seat = seat;
    }
    public String getseat(){
        return this.seat;
    }  
    public static String formatSeat(String seat) {
        seat = seat.toUpperCase().trim(); 
        if (seat.matches("S\\d") && seat.length() == 2) { 
            return "S0" + seat.substring(1); 
        }
        return seat; 
    }    
    public static ArrayList<String> forSave(ArrayList<Seat> seats){
        ArrayList<String> s = new ArrayList<>();
        for(Seat seat : seats){
            s.add(seat.getseat());
        }
        return s;
    }
}
