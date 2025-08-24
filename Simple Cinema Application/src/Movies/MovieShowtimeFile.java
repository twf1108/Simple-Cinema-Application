package Movies;

import java.io.*;
import java.util.*;

import Admin.MovieModify;
import Admin.ShowtimeModify;

import java.time.*;
import Module.FileHandle;

public class MovieShowtimeFile {

    public static void saveToFile(Movie movie) {
        movieDestination();
        String fileName = "movieFolder/" + movie.getTitle() + ".txt";

        try {
            File myFile = new File(fileName);

            try (FileWriter writer = new FileWriter(myFile, false)) {
                writer.write(movie.toFileString());
            }
            System.out.println("Information added successfully ");

        } catch (IOException e) {
            System.out.println("Error saving information!");
        }
    }

    public static Movie fileToString(String filePath) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("movieFolder/" + filePath))) {
            String movieLine = br.readLine();
            if (movieLine == null) {
                System.out.println("Empty file: " + filePath);
                return null;
            }

            List<String> movieData = FileHandle.parseLine(movieLine);

            String line;
            while ((line = br.readLine()) != null) {
                List<String> parts = FileHandle.parseLine(line);
                if (parts.size() == 3) {
                    Showtime showtime = new Showtime(parts.get(0), parts.get(1), parts.get(2));
                    showtimes.add(showtime);
                }
            }

            Price price = new Price(
                    Double.parseDouble(movieData.get(7)),
                    Double.parseDouble(movieData.get(8)),
                    Double.parseDouble(movieData.get(9))
            );

            // Create and return the Movie object
            Movie movie = new Movie(
                    movieData.get(0).replace("[", "").replace("]", ""),
                    movieData.get(1).replace("[", "").replace("]", ""),
                    Integer.parseInt(movieData.get(2)),
                    movieData.get(3).replace("[", "").replace("]", ""),
                    movieData.get(4).replace("[", "").replace("]", ""),
                    movieData.get(5).replace("[", "").replace("]", ""),
                    movieData.get(6).replace("[", "").replace("]", ""),
                    price, showtimes
            );

            return movie;

        } catch (IOException e) {
            System.out.println("Error opening or reading the file: " + filePath);
            return null;
        }
    }

    // Movie
    //Movie txt file destination
    public static File[] movieDestination() {
        File folder = new File("movieFolder");
        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }
        return folder.listFiles();
    }

    //Array list to store movie title
    public static ArrayList<String> movieList() {
        File[] listOfFiles = movieDestination();
        ArrayList<String> movieList = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    movieList.add(file.getName());
                }
            }
        } else {
            System.out.println("No movies available.");
        }
        return movieList;
    }

    //Method to show detail of selected movie
    public static void movieDetail(String select) {
        Movie movie = fileToString(select);
        System.out.println(movie.toString());
    }

    //Method to show available movie(only title)
    public static void viewMovieList() {
        System.out.println("\n\n== List of Movies ==");
                for (int i = 0; i < movieList().size(); i++) {
            System.out.println((i + 1) + ". " + movieList().get(i).replace(".txt",""));
        }
    }

    // Showtime
    //Show showtime of selected movie
    public static void showMovieTime(Scanner scanner) {
        viewMovieList();
        String select = MovieModify.movieChoice(scanner);
        if (select.equals("")) {
            return;
        }
        String movieFile = select;
        Movie movie = fileToString(movieFile);
        ArrayList<Showtime> showtimes = movie.getShowtimes();

        if (showtimes.isEmpty()) {
            System.out.println("No showtime available for this movie!");
        } else {
            System.out.println("\n\n\nSelected movie: " + select.replace(".txt", ""));
            System.out.println("********************************************************");
            System.out.printf("|      %-10s |      %-12s |      %-9s |\n", "Hall", "Date", "Time");
            System.out.println("********************************************************");
            for (Showtime showtime : showtimes) {
                System.out.println(showtime.toString());
            }
            System.out.println("********************************************************\n\n");
        }

    }

    //Show available showtime of selected hall
    public static void showHallTime(Scanner scanner) {
        ArrayList<String> movieList = movieList();
        String hallString = ShowtimeModify.hallChoice(scanner);
        boolean found = false;

        if (hallString == null) {
            System.out.println("Invalid choice");
        } else {
            System.out.println("\n\n\n********************************************************");
            System.out.printf("| %-20s | %-12s | %-9s |\n", "Movie", "Date", "Time");
            System.out.println("********************************************************");

            for (String movies : movieList) {
                Movie movie = fileToString(movies);

                ArrayList<Showtime> showtimes = movie.getShowtimes();

                for (Showtime showtime : showtimes) {
                    if (showtime.getHall().equals(hallString)) {
                        System.out.printf("| %-20s | %-12s | %-9s |\n",
                                movie.getTitle(), showtime.getDate(), showtime.getShowtime());
                        found = true;
                    }
                }
            }
            System.out.println("********************************************************\n\n");

            if (!found) {
                System.out.println("No showtime available for this hall");
            }
        }
    }

    //Show available showtime of selected date
    public static void showDate(Scanner scanner) {
        ArrayList<String> movieList = movieList();
        boolean found = false;
        String select = ShowtimeModify.dateChoice(scanner);

        if (select == null) {
            return;
        }

        LocalDate selectedDate = LocalDate.parse(select, ShowtimeModify.getDateFormatter());
        System.out.println("\n\n\nSelected date (YYYY/MM/DD): " + select);
        System.out.println("********************************************************");
        System.out.printf("| %-20s | %-12s | %-9s |\n", "Movie", "Hall", "Time");
        System.out.println("********************************************************");

        for (String movies : movieList) {
            Movie movie = fileToString(movies);
            if (movie == null) {
                continue;
            }

            ArrayList<Showtime> showtimes = movie.getShowtimes();

            for (Showtime showtime : showtimes) {
                LocalDate showtimeDate = LocalDate.parse(showtime.getDate(), ShowtimeModify.getDateFormatter());
                if (showtimeDate.equals(selectedDate)) {
                    System.out.printf("| %-20s | %-12s | %-9s |\n",
                            movie.getTitle(), showtime.getHall(), showtime.getShowtime());
                    found = true;
                }
            }
        }

        System.out.println("********************************************************\n\n");

        if (!found) {
            System.out.println("No showtimes found for the selected date.");
        }
    }

}
