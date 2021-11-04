import Exceptions.WrongDateException;

import java.io.*;
import java.util.List;


public class Menu {
    private static final String flightsTxt = "src/main/resources/flights.txt";

    public void showMenu() {
        Flights flight = new Flights();
        // de aici inlocuiesc cu meniu
        readFiles(flight);
        flight.showFlights();
        System.out.println();
        flight.deleteFlight(2);
        flight.showFlights();
        tryUpdateFlight(flight, 1, "ATH55A", "London", "Bucuresti", 3700, 550, 22, 15);
        System.out.println();
        flight.showFlights();
    }

    public void tryAddFlight(Flights flight, String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes) {
        try {
            flight.addFlight(plane, departureCity, destinationCity, distance, average, hour, minutes);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tryUpdateFlight(Flights flight, int index, String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes) {
        try {
            flight.updateFlight(index, plane, departureCity, destinationCity, distance, average, hour, minutes);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFiles(Flights flight) {

        Runnable readFlights = () -> {
            File file = new File(flightsTxt);
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(","));
                    tryAddFlight(flight, list.get(0), list.get(1), list.get(2),
                            Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)),
                            Integer.parseInt(list.get(6)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread readFlightsThread = new Thread(readFlights);

        readFlightsThread.start();
        try {
            readFlightsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
