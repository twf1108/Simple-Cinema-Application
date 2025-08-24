package Admin;

import java.io.*;
import java.util.*;

import Module.FileHandle;
import Module.IAndOUtil;
import Movies.Movie;
import Movies.MovieShowtimeFile;
import Movies.Price;
import Movies.Showtime;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class ShowtimeModify {

    private static final int START_HOUR = 10; // 10:00 AM
    private static final int END_HOUR = 23;  // 11:00 PM
    private static final int MOVIE_INTERVAL = 30;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    //To round up the end time
    private static LocalTime roundUp(LocalTime time, int interval) {
        int minute = time.getMinute();
        int remainder = minute % interval;

        if (remainder != 0) {
            time = time.plusMinutes(interval - remainder);
        }

        return time;
    }

    public static String hallChoice(Scanner scanner) {
        System.out.println("\n\n== Available Hall ==");
        for (int i = 0; i < Showtime.getHALL_LIST().size(); i++) {
            System.out.println((i + 1) + ". " + Showtime.getHALL_LIST().get(i));
        }

        try {
            int hall = IAndOUtil.readInt(scanner, "Enter the hall number ( or enter 0 to exit ): ", 0, Showtime.getHALL_LIST().size());
            scanner.nextLine();
            if (hall == 0) {
                System.out.println("Exiting...");
                return null;
            }
            return Showtime.getHALL_LIST().get(hall - 1);
        } catch (InputMismatchException ex) {
            return null;
        }
    }

    public static String dateChoice(Scanner scanner) {
        List<String> dateList = new ArrayList<>();
        LocalDate startDate = LocalDate.parse("2024/05/15", DATE_FORMATTER);

        for (int i = 0; i < 7; i++) {
            dateList.add(startDate.plusDays(i + 1).format(DATE_FORMATTER));
        }

        System.out.println("\n\n== Available Dates ==");
        for (int i = 0; i < dateList.size(); i++) {
            System.out.println((i + 1) + ". " + dateList.get(i));
        }

        try {
            int dateChoice = IAndOUtil.readInt(scanner, "Select a date by entering the number( or 0 to exit ): ", 0, dateList.size());
            scanner.nextLine();

            if (dateChoice == 0) {
                System.out.println("Exiting...");
                return null;
            }
            return dateList.get(dateChoice - 1);

        } catch (InputMismatchException ex) {
            return null;
        }
    }

    public static void addShowtime(Scanner scanner) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        MovieShowtimeFile.viewMovieList();
        String selectedMovie = MovieModify.movieChoice(scanner);
        if (selectedMovie == null || selectedMovie.isEmpty()) {
            return;
        }

        String selectedDate = dateChoice(scanner);
        if (selectedDate == null || selectedDate.isEmpty()) {
            return;
        }

        String selectedHall = hallChoice(scanner);
        if (selectedHall == null || selectedHall.isEmpty()) {
            return;
        }

        // Generate time slots
        ArrayList<String> availableTimes = new ArrayList<>();
        for (int hour = START_HOUR; hour < END_HOUR; hour++) {
            for (int minute = 0; minute < 60; minute += MOVIE_INTERVAL) {
                String timeSlot = String.format("%02d%02d", hour, minute);
                availableTimes.add(timeSlot);
            }
        }

        // Check all movie files to remove conflicting time slots
        ArrayList<String> movieList = MovieShowtimeFile.movieList();
        for (String movieFile : movieList) {
            Movie movie = MovieShowtimeFile.fileToString(movieFile);
            int duration = movie.getDuration();
            ArrayList<Showtime> showtimeData = movie.getShowtimes();

            for (Showtime showtime : showtimeData) {
                String existingTime = showtime.getShowtime();
                String existingDate = showtime.getDate();
                String existingHall = showtime.getHall();
                LocalTime startTime = LocalTime.parse(existingTime, timeFormatter);
                LocalTime endTime = roundUp(startTime.plusMinutes(duration), MOVIE_INTERVAL);

                if (selectedDate.equals(existingDate) && selectedHall.equals(existingHall)) {
                    int startIndex = -1, endIndex = -1;

                    for (int i = 0; i < availableTimes.size(); i++) {
                        if (availableTimes.get(i).equals(startTime.format(timeFormatter))) {
                            startIndex = i;
                        }
                        if (availableTimes.get(i).equals(endTime.format(timeFormatter))) {
                            endIndex = i;
                            break;
                        }
                    }

                    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                        availableTimes.subList(startIndex, endIndex + 1).clear();
                    }
                }
            }
        }

        if (availableTimes.isEmpty()) {
            System.out.println("No showtime available for this date and hall");
            return;
        } else {
            System.out.println("\n\n== Available showtimes ==");
            for (int i = 0; i < availableTimes.size(); i++) {
                System.out.println((i + 1) + ". " + availableTimes.get(i));
            }

        }

        // Let user choose a showtime
        try {
            int startShowtimeChoice = IAndOUtil.readInt(scanner, "Enter the start showtime ( or 0 to exit ): ", 0, availableTimes.size());
            scanner.nextLine();

            if (startShowtimeChoice == 0){
                System.out.println("Exiting...");
                return;
            }
            String selectedStartShowtime = availableTimes.get(startShowtimeChoice - 1);

            Movie movie = MovieShowtimeFile.fileToString(selectedMovie);
            ArrayList<Showtime> showtimeData = movie.getShowtimes();

            Showtime newShowtime = new Showtime(selectedStartShowtime, selectedDate, selectedHall);
            showtimeData.add(newShowtime);

            Price price = movie.getPrice();

            Movie updatedMovie = new Movie(
                    movie.getTitle(), movie.getGenre(), movie.getDuration(),
                    movie.getDirector(), movie.getLanguage(), movie.getSynopsis(), movie.getCast(),
                    price, showtimeData
            );

            File delFile = new File("movieFolder/" + selectedMovie);
            delFile.delete();

            MovieShowtimeFile.saveToFile(updatedMovie);

        } catch (InputMismatchException ex) {
            scanner.next(); // Clear invalid input
        }
    }

    public static void viewShowtime(Scanner scanner) {
        int select;

        System.out.println("\n\n1. Movie    2. Hall    3. Date");
        System.out.print("Enter the number of your choice ( or 0 to exit ): ");

        try {
            select = scanner.nextInt();
            scanner.nextLine(); // clear the newline
        } catch (InputMismatchException ex) {
            System.out.println("Please enter an integer.");
            return; // exit early if input is invalid
        }

        switch (select) {
            case 0:
                System.out.println("Exiting....");
                return;
            case 1:
                MovieShowtimeFile.showMovieTime(scanner);
                break;
            case 2:
                MovieShowtimeFile.showHallTime(scanner);
                break;
            case 3:
                MovieShowtimeFile.showDate(scanner);
                break;
            default:
                System.out.println("Please enter a valid choice.");
        }
    }

    public static void deleteShowtime(Scanner scanner) {
        ArrayList<String> movieFiles = MovieShowtimeFile.movieList();
        ArrayList<String> deletableMovies = new ArrayList<String>();

        // Collect all booked showtimes from TicketRecord.txt
        HashSet<String> booked = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("TicketRecord.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> parts = FileHandle.parseLine(line);
                if (parts.size() >= 4) {
                    String movie = parts.get(1).trim();
                    String date = parts.get(2).trim();
                    String time = parts.get(3).trim();
                    booked.add(movie + "|" + date + "|" + time);  // Add to booked set
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading TicketRecord.txt");
            return;
        }

        // Iterate through movie files and find deletable showtimes
        for (String fileName : movieFiles) {
            Movie movie = MovieShowtimeFile.fileToString(fileName);
            if (movie == null) {
                continue;
            }

            ArrayList<Showtime> original = movie.getShowtimes();
            List<Showtime> deletableShowtimes = new ArrayList<>();

            // Identify unbooked showtimes (deletable)
            for (Showtime showtime : original) {
                String key = movie.getTitle() + "|" + showtime.getDate() + "|" + showtime.getShowtime();
                if (!booked.contains(key)) {
                    deletableShowtimes.add(showtime);  // Unbooked showtimes are deletable
                }
            }

            if (!deletableShowtimes.isEmpty()) {
                deletableMovies.add(fileName);  // Add movie to the deletable list if it has deletable showtimes
            }
        }

        // If there are deletable movies, prompt the admin to select one
        if (!deletableMovies.isEmpty()) {
            System.out.println("\n== Movies with deletable showtimes ==");

            // List movies with deletable showtimes
            for (int i = 0; i < deletableMovies.size(); i++) {
                Movie movie = MovieShowtimeFile.fileToString(deletableMovies.get(i));
                System.out.println((i + 1) + ". " + movie.getTitle());
            }

            // Prompt the admin to select a movie
            System.out.print("Enter the number of the movie to delete a showtime (or 0 to cancel): ");
            int movieChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (movieChoice == 0) {
                System.out.println("Aborting deletion.");
                return;
            }

            if (movieChoice < 1 || movieChoice > deletableMovies.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            // Get the selected movie
            String selectedMovieFile = deletableMovies.get(movieChoice - 1);
            Movie selectedMovie = MovieShowtimeFile.fileToString(selectedMovieFile);

            // List deletable showtimes for the selected movie
            ArrayList<Showtime> showtimes = selectedMovie.getShowtimes();
            List<Showtime> deletableShowtimes = new ArrayList<>();

            // Identify deletable showtimes
            for (Showtime showtime : showtimes) {
                String key = selectedMovie.getTitle() + "|" + showtime.getDate() + "|" + showtime.getShowtime();
                if (!booked.contains(key)) {
                    deletableShowtimes.add(showtime);
                }
            }

            // Show available showtimes for deletion
            if (!deletableShowtimes.isEmpty()) {
                System.out.println("\nAvailable showtimes to delete for movie: " + selectedMovie.getTitle());
                for (int i = 0; i < deletableShowtimes.size(); i++) {
                    Showtime showtime = deletableShowtimes.get(i);
                    System.out.println((i + 1) + ". " + showtime.getDate() + " " + showtime.getShowtime() + " in " + showtime.getHall());
                }

                // Prompt the admin to select a showtime to delete
                System.out.print("Enter the number of the showtime to delete (or 0 to cancel): ");
                int showtimeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (showtimeChoice > 0 && showtimeChoice <= deletableShowtimes.size()) {
                    Showtime showtimeToDelete = deletableShowtimes.get(showtimeChoice - 1);
                    showtimes.remove(showtimeToDelete);  // Remove the selected showtime from the movie's showtimes
                    selectedMovie.setShowtimes(showtimes);  // Update the movie's showtimes list

                    // Save the updated movie file
                    MovieShowtimeFile.saveToFile(selectedMovie);
                    System.out.println("Deleted showtime: " + showtimeToDelete.getDate() + " " + showtimeToDelete.getShowtime());
                } else {
                    System.out.println("No valid choice made. Aborting deletion.");
                }
            } else {
                System.out.println("No deletable showtimes found for movie: " + selectedMovie.getTitle());
            }
        } else {
            System.out.println("No movies with deletable showtimes found.");
        }
    }

    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }
}
