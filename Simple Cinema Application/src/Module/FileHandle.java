package Module;
import java.io.*;
import java.util.*;

import Booking.Date;
import Booking.Hall;
import Booking.Seat;
import Booking.Ticket;
import Booking.Time;
import Movies.Movie;
import Movies.Price;


public class FileHandle {
    
    String information;
    
    public FileHandle(){}
    public FileHandle(String information){
        this.information = information;
    }
    
    public void writeIntoFile(String fileName){

        try(FileWriter writer = new FileWriter(fileName,true)){
            writer.write(this.information);
        }
        catch(IOException ex){
            System.out.println("Could not write the file");
        }
    }
    
    public static ArrayList<String> readFromFile(String fileName){
        ArrayList<String> listLine = new ArrayList<String>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"))){
            String line;
            while((line = reader.readLine()) != null){
                if (line.contains("[")) {
                    ArrayList<String> parts = parseLine(line);
                    listLine.addAll(parts);
                } else {
                    for(String part : line.split(",")){
                        listLine.add(part.trim());
                    }
                }
            }       
        }
        catch(IOException ex){
            System.out.println("Could not read the file cause of " + ex);
        }
        return listLine;
    }
    
    public static Movie readMovie(ArrayList<String> movies,int selection){        
        ArrayList<String> fileMovie = readFromFile("movieFolder/" + movies.get(selection - 1 ));
        
        if(fileMovie.size() >= 10){
               return new Movie(fileMovie.get(0).replace("[", "").replace("]", ""), 
                                fileMovie.get(1).replace("[", "").replace("]", ""),
                                Integer.parseInt(fileMovie.get(2)),
                                fileMovie.get(3).replace("[", "").replace("]", ""), 
                                fileMovie.get(4).replace("[", "").replace("]", ""),
                                fileMovie.get(5).replace("[", "").replace("]", ""), 
                                fileMovie.get(6).replace("[", "").replace("]", ""), 
                                new Price(Double.parseDouble(fileMovie.get(7)), 
                                Double.parseDouble(fileMovie.get(8)), 
                                Double.parseDouble(fileMovie.get(9))));
        }
        return null;
    }
    
    public static ArrayList<Date> readDate(Ticket ticket){       
        ArrayList<String> fileDates = readFromFile("movieFolder/" + ticket.getMovie().getTitle());
        ArrayList<Date> dateInformations = new ArrayList<>();
        
        if(fileDates.size() > 11 ){
            for(int i = 11; i < fileDates.size(); i+=3){
                if(!dateCheck(dateInformations, fileDates.get(i))) dateInformations.add(new Date(fileDates.get(i)));
            }
        }   
        Collections.sort(dateInformations, (date1, date2) -> date1.getDate().compareTo(date2.getDate()));
        return dateInformations;
    }
    
    private static boolean dateCheck(ArrayList<Date> dateInformations, String fileDates){
        int index = 0;
        if(!dateInformations.isEmpty()){
            for(Date date : dateInformations){
                if(date.getDate().equals(fileDates)) return true;               
                index++;
            }
        }
        return false;
    }

    public static ArrayList<Time> readTime(Ticket ticket){

        ArrayList<String> fileMovie = readFromFile("movieFolder/" + ticket.getMovie().getTitle());
        ArrayList<Time> timesList = new ArrayList<>();
        for(int i = 10; i < fileMovie.size(); i+=3){
            if((fileMovie.get(i+1)).equals(ticket.getDate().getDate())){
                timesList.add(new Time(fileMovie.get(i)));               
            }
        } 
        Collections.sort(timesList, (time1,time2) -> time1.getTime().compareTo(time2.getTime()));
        return  timesList; 
    }    
    
    public static Hall readHall(Ticket ticket){
        ArrayList<String> fileInformation = FileHandle.readFromFile("movieFolder/" + ticket.getMovie().getTitle());        

        for(int i = 10; i < fileInformation.size(); i+=3){
            if(fileInformation.get(i).equals(ticket.getTime().getTime()) && fileInformation.get(i+1).equals(ticket.getDate().getDate())){  
                return new Hall(fileInformation.get(i+2));
            }
        }
        return new Hall("\u001B[31mWarning something error please contact us(+9588101)!!!\u001B[0m");
    }
    
    public static ArrayList<Seat> readSeat(Ticket ticket, String fileName) {
        ArrayList<Seat> dataList = new ArrayList<>();  
        final int USER_INDEX = 1;    
        final int DATE_INDEX = 2;   
        final int TIME_INDEX = 3;    
        final int HALL_INDEX = 4;    
        final int TARGET_INDEX = 5;  

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> parts = parseLine(line);

                if (ticket.getMovie().getTitle() != null && !parts.get(USER_INDEX).trim().equals(ticket.getMovie().getTitle())) continue; 
                if (ticket.getDate().getDate() != null && !parts.get(DATE_INDEX).trim().equals(ticket.getDate().getDate())) continue;
                if (ticket.getTime().getTime() != null && !parts.get(TIME_INDEX).trim().equals(ticket.getTime().getTime())) continue;               
                if (ticket.getHall().getHallNo() != null && !parts.get(HALL_INDEX).trim().equals(ticket.getHall().getHallNo())) continue;
                
                String seats = parts.get(TARGET_INDEX).trim();
                if (seats.startsWith("[") && seats.endsWith("]")) {
                    String[] seatArray = seats.substring(1, seats.length()-1).split(",");
                    for (String seat : seatArray) {
                        dataList.add(new Seat(seat.trim()));
                    }
                } else {
                    dataList.add(new Seat(seats));
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return dataList;
    }
 
    public static ArrayList<String> parseLine(String line) {
        ArrayList<String> parts = new ArrayList<>();
        boolean inBracket = false;
        StringBuilder currentPart = new StringBuilder();

        for (char c : line.toCharArray()) {
            
            if (c == '[') {
                inBracket = true;
            }
            else if (c == ']'){
                inBracket = false;
            }
            if (c == ',' && !inBracket) {
                parts.add(currentPart.toString());
                currentPart.setLength(0);
            } 
            else {
                currentPart.append(c);
            }
        }
        parts.add(currentPart.toString());
        return parts;
    }
    
 
    public static ArrayList<String> viewRecord(String username){
        ArrayList<String> userTicket = new ArrayList<>();
        ArrayList<String> allRecords = readFromFile("UserTicket");

        for(int i = 0; i < allRecords.size(); i+=2){
            if(i+1 < allRecords.size() && allRecords.get(i).equals(username)) {
                userTicket.add(allRecords.get(i+1));
            }
        }
        return userTicket;      
    }
    
    public static void getSpecificRecord(String ticketId){
        ArrayList<String> userTicket = new ArrayList<>();
        ArrayList<String> allRecords = readFromFile("TicketRecord");

        for(int i = 0; i < allRecords.size(); i += 8){
            if(allRecords.get(i).equals(ticketId)){
                for(int j = i; j <= i+7; j++){
                    userTicket.add(allRecords.get(j));
                }
            }                  
        }
        if(userTicket.isEmpty()) System.out.println("\nSomething error sorry please contact customer service or call 9588101...");
        else{
                System.out.println("\n== Ticket Details ==");
                System.out.println("Ticket ID: " + userTicket.get(0));
                System.out.println("Movie    : " + userTicket.get(1));
                System.out.println("Date     : " + userTicket.get(2));
                System.out.println("Time     : " + userTicket.get(3));
                System.out.println("Hall     : " + userTicket.get(4));
                System.out.println("Seat     : " + userTicket.get(5));
                String food = userTicket.get(7).equals("[]") ? "-" : userTicket.get(7);
                System.out.println("Food     : " + food );
                
        }      
    }
    
    public static void deleteTicket(String ticketId) {
        ArrayList<String> allLines = new ArrayList<>();
        String ticketToRemove = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("TicketRecord.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(ticketId + ",")) {
                    ticketToRemove = line;
                    continue; 
                }
                allLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Could not read the file: " + ex.getMessage());
            return;
        }
        
        if (ticketToRemove != null) {
            try (FileWriter writer = new FileWriter("TicketRecord.txt")) {
                for (String line : allLines) {
                    writer.write(line + "\n");
                }
                System.out.println("\n[✓] Ticket " + ticketId + " has been successfully refunded.");
            } catch (IOException ex) {
                System.out.println("Could not write to the file: " + ex.getMessage());
            }
            removeFromUserTicket(ticketId);
        } else {
            System.out.println("\n[!] Ticket " + ticketId + " not found.");
        }
    }

    private static void removeFromUserTicket(String ticketId) {
        ArrayList<String> allLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("UserTicket.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("," + ticketId)) {
                    found = true;
                    continue;
                }
                allLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Could not read UserTicket.txt: " + ex.getMessage());
            return;
        }
        
        if (found) {
            try (FileWriter writer = new FileWriter("UserTicket.txt")) {
                for (String line : allLines) {
                    writer.write(line + "\n");
                }
                System.out.println("[✓] The amount will be refunded to your payment method.");
            } catch (IOException ex) {
                System.out.println("Could not write to UserTicket.txt: " + ex.getMessage());
            }
        } else {
            System.out.println("[!] No user association found for ticket " + ticketId);
        }
    }
}


