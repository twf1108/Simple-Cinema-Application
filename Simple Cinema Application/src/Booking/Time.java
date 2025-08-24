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
public class Time {
    private String time;
    
    public Time(){}
    public Time(String time){
        this.time = time;
    }   
    
    public void setTime(String time){
        this.time = time;
    }    
    public String getTime(){
        return this.time;
    }   
    
    public void displayValidTimes(ArrayList<Time> times){
        int index = 1;
        System.out.println("\n == Time slot == ");
        for(Time time : times){
            if(index < 10) System.out.println("0" + index + ". "+ time.getTime());
            else System.out.println(index + ". "+ time.getTime());  
            index++;
        }
    }
}
