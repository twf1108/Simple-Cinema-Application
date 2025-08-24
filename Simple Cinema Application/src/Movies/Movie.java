package Movies;
import java.util.*;

public class Movie{
    private String title, genre, director, language, synopsis, cast;
    private int duration;
    private Price price;
    private ArrayList<Showtime> showtimes;
    private static final String[] INFORMATION = {"Language", "Synopsis", "Cast"};
    
    
    //Constructor
    public Movie(String title, String genre, int duration, String director, String language, 
            String synopsis, String cast, Price price) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.director = director;
        this.language = language;
        this.synopsis = synopsis;
        this.cast = cast;     
        this.price = price;                
        this.showtimes = new ArrayList<>();
    }
    
    public Movie(String title, String genre, int duration, String director, String language, String synopsis, String cast, Price price, ArrayList<Showtime> showtimes) {         
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.director = director;
        this.language = language;
        this.synopsis = synopsis;
        this.cast = cast;     
        this.price = price;
        this.showtimes = showtimes;
    }
    
    //Getter
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getLanguage() {
        return language;
    }

    public int getDuration() {
        return duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCast() {
        return cast;
    }    

    public Price getPrice() {
        return price;
    }

    public ArrayList<Showtime> getShowtimes() {
        return showtimes;
    }

    public static String[] getINFORMATION() {
        return INFORMATION;
    }
    

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setShowtimes(ArrayList<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
    
    

    
    public String showtimeString(){
        String showtimeString = "";
  
        for (Showtime showtime : showtimes){
            showtimeString += showtime.toFileString();       
        }
        return showtimeString;
    }

    public String toFileString() {
        return "[" + title + "],[" + genre + "]," + duration + ",[" + director + "],[" + language + "],[" + synopsis + "],[" + cast + "]," +
                price.toFileString() +
                showtimeString();
    }
    
    @Override
    public String toString(){
        return "\n == Movie Information ==" + 
               "\nTitle    :" + this.getTitle() +
               "\nGenre    :" + this.getGenre() +
               "\nDuration :" + this.getDuration() +
               "\nDirector :" + this.getDirector() +
               "\nLanguage :" + this.getLanguage() +
               "\nSynopsis :" + this.getSynopsis() +
               "\nCast     :" + this.getCast() +
               "\nPrice(child, adult, senior) :" + "RM " + this.getPrice().getChildPrice() + ", RM " + this.getPrice().getAdultPrice() +
                ", RM " + this.getPrice().getSeniorPrice();
    }
}
