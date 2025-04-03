import java.util.*;
import java.util.concurrent.*;

class Flight {
    String flightName;
    int seatsAvailable;

    public Flight(String name, int seats) {
        this.flightName = name;
        this.seatsAvailable = seats;
    }

    @Override
    public String toString() {
        return flightName + " (Seats available: " + seatsAvailable + ")";
    }
}

class FlightBookingSystem {
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    private static final List<Flight> flights = Arrays.asList(
        new Flight("Flight A", 5),
        new Flight("Flight B", 10)
    );

    public static Future<List<Flight>> searchFlights() {
        return executor.submit(() -> {
            Thread.sleep(2000); // Simulating delay in fetching flights
            return flights;
        });
    }

    public static void main(String[] args) {
        System.out.println("Searching for flights...");

        Future<List<Flight>> futureFlights = searchFlights();

        try {
            List<Flight> availableFlights = futureFlights.get(); // Wait for result
            System.out.println("Available flights: " + availableFlights.size());
            for (Flight flight : availableFlights) {
                System.out.println(flight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
