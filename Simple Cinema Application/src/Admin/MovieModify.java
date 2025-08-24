package Admin;

import java.io.*;
import java.util.*;

import Module.IAndOUtil;
import Movies.Movie;
import Movies.MovieShowtimeFile;
import Movies.Price;

public class MovieModify {

    public static void addMovie(Scanner scanner) {
        ArrayList<String> movieList = MovieShowtimeFile.movieList();

        // Prompt input Movie Information //
        System.out.print("Enter Movie Title: ");
        String title = scanner.nextLine().trim();
        // Validate if title is empty. 
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty. ");
            return;
        }

        String genre = getStringInput(scanner, "Enter Genre: ");
        if (genre.isEmpty()) {
            System.out.println("Genre cannot be empty. ");
            return;
        }

        // Duration input with validation
        int duration = getIntInput(scanner, "Enter Duration (minutes): ");
        scanner.nextLine();

        String director = getStringInput(scanner, "Enter Director Name: ");

        System.out.print("Enter Language (or press enter for default language: English): ");
        String language = scanner.nextLine();
        if (language.equals("")) {
            language = "English";
        }

        String synopsis = getStringInput(scanner, "Enter Synopsis: ");

        String cast = getStringInput(scanner, "Enter Main Casts: ");

        // Child price input with validation
        System.out.print("Enter the child price (or press enter for default ticket price: 10.0): ");
        String childPriceInput = scanner.nextLine();
        double childPrice;

        while (true) {
            if (childPriceInput.equals("")) {
                childPrice = 10.0;
                break;
            } else {
                try {
                    childPrice = Double.parseDouble(childPriceInput);
                    break;
                } catch (NumberFormatException ex) {
                    System.out.print("Please enter a valid number for child price: ");
                    childPriceInput = scanner.nextLine();
                }
            }
        }

        // Adult price input with validation
        System.out.print("Enter the adult price (or press enter for default ticket price: 15.0): ");
        String adultPriceInput = scanner.nextLine();
        double adultPrice;

        while (true) {
            if (adultPriceInput.equals("")) {
                adultPrice = 15.0;
                break;
            } else {
                try {
                    adultPrice = Double.parseDouble(adultPriceInput);
                    break;
                } catch (NumberFormatException ex) {
                    System.out.print("Please enter a valid number for adult price: ");
                    adultPriceInput = scanner.nextLine();
                }
            }
        }

        // Senior price input with validation
        System.out.print("Enter the senior price (or press enter for default ticket price: 10.0): ");
        String seniorPriceInput = scanner.nextLine();
        double seniorPrice;

        while (true) {
            if (seniorPriceInput.equals("")) {
                seniorPrice = 10.0;
                break;
            } else {
                try {
                    seniorPrice = Double.parseDouble(seniorPriceInput);
                    break;
                } catch (NumberFormatException ex) {
                    System.out.print("Please enter a valid number for senior price: ");
                    seniorPriceInput = scanner.nextLine();
                }
            }
        }
        //              Prompt Input Information End                //

        boolean movieExists = false;
        for (String movie : movieList) {
            if ((title + ".txt").equals(movie)) {
                movieExists = true;
                break;
            }
        }

        if (!movieExists) {
            Price price = new Price(childPrice, adultPrice, seniorPrice);
            Movie newMovie = new Movie(title, genre, duration, director, language, synopsis, cast, price);
            MovieShowtimeFile.saveToFile(newMovie);
        } else {
            System.out.println("Movie already exists.");
        }
    }

    public static void viewMovie(Scanner scanner) {
        MovieShowtimeFile.viewMovieList();
        String select = MovieModify.movieChoice(scanner);
        if (select.equals("")) {
            return;
        }
        MovieShowtimeFile.movieDetail(select);
    }

    public static void delMovie(Scanner scanner) {
        ArrayList<String> allMovies = MovieShowtimeFile.movieList();
        Set<String> bookedMovieNames = new HashSet<>();

        // Read TicketRecord.txt and extract all movie names with bookings
        try (BufferedReader br = new BufferedReader(new FileReader("TicketRecord.txt"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    bookedMovieNames.add(parts[1].trim());  // Movie name
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading TicketRecord.txt: " + e.getMessage());
        }

        // Build deletable movie list
        ArrayList<String> deletableMovies = new ArrayList<>();
        for (String movieFile : allMovies) {
            String movieTitle = movieFile.replace(".txt", "").trim();
            if (!bookedMovieNames.contains(movieTitle)) {
                deletableMovies.add(movieFile);  // still include full file name
            }
        }

        if (deletableMovies.isEmpty()) {
            System.out.println("\nNo movies available for deletion (all have bookings).\n");
            return;
        }
        
        // Show list  
        System.out.println("\n\n== Available movies to be delete ==");
        for (int i = 0; i < deletableMovies.size(); i++) {
            System.out.println((i + 1) + ". " + deletableMovies.get(i).replace(".txt", ""));
        }

        // Prompt admin
        try { 
            int choice = IAndOUtil.readInt(scanner, "Enter the number of the movie to delete (or 0 to cancel): ", 0 , deletableMovies.size());
            scanner.nextLine(); 
            if (choice == 0){
                System.out.println("Exiting...");
                return;
            }
            
            String fileToDelete = deletableMovies.get(choice - 1);
            File delFile = new File("movieFolder/" + fileToDelete);
            if (delFile.exists() && delFile.delete()) {
                System.out.println("\nMovie deleted successfully.\n");
            } else {
                System.out.println("\nFailed to delete the movie.\n");
            }
            
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
    }

    public static void modifyMovie(Scanner scanner) {
        MovieShowtimeFile.viewMovieList();
        String select = movieChoice(scanner);
        if (select.equals("")) {
            return;
        }

        Movie movie = MovieShowtimeFile.fileToString(select);
        IAndOUtil.displayMenu("\n\n== Movie information that can be modified ==", Movie.getINFORMATION());

        System.out.print("Choose information that you want to modify( or 0 to exit ): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 0:
                System.out.println("Exiting...");
                return;
            case 1:
                System.out.print("\nEnter the new language: ");
                String newLanguage = scanner.nextLine();
                movie.setLanguage("[" + newLanguage + "]");
                MovieShowtimeFile.saveToFile(movie);
                break;
            case 2:
                System.out.print("\nEnter the new synopsis: ");
                String newSynopsis = scanner.nextLine();
                movie.setSynopsis("[" + newSynopsis + "]");
                MovieShowtimeFile.saveToFile(movie);
                break;
            case 3:
                System.out.print("\nEnter the new cast: ");
                String newCast = scanner.nextLine();
                movie.setCast("[" + newCast + "]");
                MovieShowtimeFile.saveToFile(movie);
                break;
            default:
                System.out.println("Invalid option");
                break;

        }
    }

    public static String movieChoice(Scanner scanner) {
        ArrayList<String> movieList = MovieShowtimeFile.movieList();

        if (movieList.isEmpty()) {
            return "";
        }

        try {
            int choice = IAndOUtil.readInt(scanner, "Enter the no of the movie ( or 0 to exit ): ", 0, movieList.size());
            scanner.nextLine();  
            if (choice == 0){
                System.out.println("Exiting...");
                return "";
            }
            return movieList.get(choice - 1);

        } catch (InputMismatchException ex) {
            return "";
        }
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer. ");
                scanner.nextLine();
            }
        }
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                return input;  // Valid input
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }
}
