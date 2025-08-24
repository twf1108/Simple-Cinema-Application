package Movies;

import java.util.*;

public class Showtime {

    private String showtime;
    private String date;
    private String hall;
    private static final List<String> HALL_LIST = Arrays.asList("H1", "H2", "H3", "H4", "H5", "H6", "H7");

    public Showtime(String showtime, String date, String hall) {
        this.showtime = showtime;
        this.date = date;
        this.hall = hall;
    }

    public String getShowtime() {
        return showtime;
    }

    public String getDate() {
        return date;
    }

    public String getHall() {
        return hall;
    }

    public static List<String> getHALL_LIST() {
        return HALL_LIST;
    }

    @Override
    public String toString() {
        return String.format("|      %-10s |    %-12s   |      %-9s |", hall, date, showtime);
    }

    public String toFileString() {
        return showtime + "," + date + "," + hall + "\n";
    }
}
