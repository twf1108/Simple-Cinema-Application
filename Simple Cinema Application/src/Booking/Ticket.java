package Booking;

import java.util.ArrayList;

import Movies.Movie;

public class Ticket {
    private Movie movie;
    private Date date;
    private Time time;
    private Hall hall;
    private SeatBuffer seat;
    private FoodExecutor foods;

    Ticket(){}

    public Movie getMovie(){
        return this.movie;
    }
    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Date getDate(){
        return this.date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public Time getTime(){
        return this.time;
    }
    public void setTime(Time time){
        this.time = time;
    }
    public void setHall(Hall hall){
        this.hall = hall;
    }
    public Hall getHall(){
        return this.hall;
    }
    public void setSeat(SeatBuffer seat){
        this.seat = seat;
    }
    public SeatBuffer getSeat(){
        return this.seat;
    }

    public void setFoods(FoodExecutor foods){
        this.foods = foods;
    }
    public FoodExecutor getFoods(){
        return this.foods;
    }

    private boolean hasFoodOrDrink() {
        if (this.foods == null || this.foods.getFoods() == null) {
            return false;
        }
        for (Object food : this.foods.getFoods()) {
            if (food instanceof SolidFood || food instanceof Drink) {
                return true;
            }
        }
        return false;
    }

    public void displayCurrentStatus() {
        System.out.print("\n == Current status == " +
                "\nMovie :" + this.movie.getTitle() +
                "\nDate  :" + this.date.getDate() +
                "\nTime  :" + this.time.getTime() +
                "\nHall  :" + this.hall.getHallNo() +
                "\nSeat  :");
        this.seat.displaySelectedSeat(this.seat.getSeats());
        System.out.print("\nType(Child, Adult, Senior) :");
        this.seat.displaySelectedTypes(this.seat);
        System.out.print("\nCurrent total(RM):" + ((this.movie.getPrice().getChildPrice() * this.seat.getTypes()[0])
                + (this.movie.getPrice().getAdultPrice() * this.seat.getTypes()[1]) +
                (this.movie.getPrice().getSeniorPrice() * this.seat.getTypes()[2])));
    }

    public void finalTicket() {
        System.out.print("\n == Ticket Info == " +
                "\nMovie :" + this.movie.getTitle() +
                "\nDate  :" + this.date.getDate() +
                "\nTime  :" + this.time.getTime() +
                "\nHall  :" + this.hall.getHallNo() +
                "\nSeat  :");
        this.seat.displaySelectedSeat(this.seat.getSeats());
        System.out.print("\nType(Child, Adult, Senior) :");
        this.seat.displaySelectedTypes(this.seat);

        System.out.print("\nFood  :");
        ArrayList<Object> solidFoodsList = new ArrayList<>();
        for (Object f : this.foods.getFoods()) {
            if (f instanceof SolidFood) {
                solidFoodsList.add(f);
            }
        }
        if (solidFoodsList.isEmpty()) {
            System.out.print(" None");
        } else {
            this.foods.displaySelected(solidFoodsList);
        }

        // Display drinks separately
        System.out.print("\nDrink :");
        ArrayList<Object> drinksList = new ArrayList<>();
        for (Object f : this.foods.getFoods()) {
            if (f instanceof Drink) {
                drinksList.add(f);
            }
        }
        if (drinksList.isEmpty()) {
            System.out.print(" None");
        } else {
            this.foods.displaySelected(drinksList);
        }

        double totalPrice = (this.movie.getPrice().getChildPrice() * this.seat.getTypes()[0])
                + (this.movie.getPrice().getAdultPrice() * this.seat.getTypes()[1])
                + (this.movie.getPrice().getSeniorPrice() * this.seat.getTypes()[2]);

        if (hasFoodOrDrink()) {
            totalPrice += this.foods.calculatePrice(this.foods.getFoods());
        }

        System.out.print("\nCurrent total(RM):" + totalPrice);
    }
    @Override
    public String toString(){
        return this.movie.getTitle() + "," + this.date.getDate() + "," + this.time.getTime() + "," + this.hall.getHallNo()+"," 
                + Seat.forSave(this.seat.getSeats()) + "," + (this.seat.getTypes()[0]+this.seat.getTypes()[1]+this.seat.getTypes()[2]) 
                + "," + FoodExecutor.forSave(this.foods);
    }
}
