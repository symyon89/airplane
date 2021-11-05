import Exceptions.WrongDateException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;


public class Menu {
    private static final String flightsTxt = "src/main/resources/flights.txt";
    private static final String fileOfPassengers = "src/main/resources/passengers.txt";
    private final Flights flight = new Flights();
    private final PassengerList passengers = new PassengerList();

    public Menu() {
        readFiles();
    }

    public void showOptions() {

        // de aici inlocuiesc cu meniu
        passengers.showPassengers();

        passengers.updatePassenger(1,"Grigoras Victor","15466546546","ATH5542",1);
        flight.showFlights();

        System.out.println();

        flight.showFlights();
        tryUpdateFlight(1, "ATH55A", "London", "Bucuresti", 3700, 550, 22, 15, 400);
        tryUpdateFlight(2, "ATH55B", "Bucuresti", "London", 3500, 500, 12, 30, 250);
        System.out.println();
        flight.showFlights();
    }
    private void showMenu(){
        System.out.println("***Menu***");
        System.out.println("1.Show Flights");
        System.out.println("2.Update Flight");
        System.out.println("3.Delete Flight");
        System.out.println("4.Show departures for one city");
        System.out.println("5.Show arrivals for one city");
        System.out.println("6.Show flights orderd by time");
    }


    private void tryAddFlight(String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes,int seats) {
        try {
            flight.addFlight(plane, departureCity, destinationCity, distance, average, hour, minutes, seats);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void tryUpdateFlight( int index, String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes, int seats) {
        try {
            flight.updateFlight(index, plane, departureCity, destinationCity, distance, average, hour, minutes, seats);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFiles() {

        Runnable readFlights = () -> {
            File file = new File(flightsTxt);
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(","));
                    tryAddFlight(list.get(0), list.get(1), list.get(2),
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
