package Booking;

import java.util.ArrayList;

public class Date {
    private String date;
    
    public Date(){}
    
    public Date(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    
    public  void setDate(String date){
        this.date = date;
    }    
    
    public boolean showDates(ArrayList<Date> dates){
        int index = 1;
        System.out.println("\n == Valid Date(yyyy/mm/dd) ==");
        if(!dates.isEmpty()){
            for(Date date : dates){
                System.out.println("0" + index + ". " + date.getDate());               
                index++;
            }
            return false;
        }
        else{
            System.out.println("Movie still ender planning...");   
            return true;
        }
    }   
}
