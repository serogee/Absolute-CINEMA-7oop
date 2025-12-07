import cinema.Cinema;

public class App {

    /**
     * Main function to start the application.
     * It creates an instance of Cinema and calls its start method.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.start();
    }

}