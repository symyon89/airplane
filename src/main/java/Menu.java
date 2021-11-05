import Exceptions.WrongDateException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;


public class Menu {
    private static final String flightsTxt = "src/main/resources/flights.txt";
    private static final String fileOfPassengers = "src/main/resources/passengers.txt";

    public void showOptions() {
        Flights flight = new Flights();
        PassengerList passengers = new PassengerList();

        readFiles(flight,passengers);
        // de aici inlocuiesc cu meniu
        passengers.showPassengers();

        passengers.updatePassenger(1,"Grigoras Victor","15466546546","ATH5542",1);
        flight.showFlights();

        System.out.println();

        flight.showFlights();
        tryUpdateFlight(flight, 1, "ATH55A", "London", "Bucuresti", 3700, 550, 22, 15, 400);
        System.out.println();
        flight.showFlights();
    }

    public void tryAddFlight(Flights flight, String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes,int seats) {
        try {
            flight.addFlight(plane, departureCity, destinationCity, distance, average, hour, minutes, seats);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tryUpdateFlight(Flights flight, int index, String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes, int seats) {
        try {
            flight.updateFlight(index, plane, departureCity, destinationCity, distance, average, hour, minutes, seats);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFiles(Flights flight,PassengerList passengers) {

        Runnable readFlights = () -> {
            File file = new File(flightsTxt);
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(","));
                    tryAddFlight(flight, list.get(0), list.get(1), list.get(2),
                            Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)),
                            Integer.parseInt(list.get(6)),Integer.parseInt(list.get(7)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Runnable readPassengers = () -> {
            File file = new File(fileOfPassengers);
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(","));
                    passengers.addPassenger(list.get(0), list.get(1), list.get(2),
                            Integer.parseInt(list.get(3)),LocalDateTime.parse(list.get(4)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread readFlightsThread = new Thread(readFlights);
        Thread readPassengersThread = new Thread(readPassengers);
        readFlightsThread.start();
        readPassengersThread.start();
        try {
            readPassengersThread.join();
            readFlightsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
