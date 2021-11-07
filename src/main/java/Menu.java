import Exceptions.WrongDateException;
import Exceptions.WrongIndexException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class Menu {
    private static final String flightsTxt = "src/main/resources/flights.txt";
    private static final String fileOfPassengers = "src/main/resources/passengers.txt";
    private final Flights flight = new Flights();
    private final PassengerList passengers = new PassengerList();
    Scanner scanner = new Scanner(System.in);

    public Menu() {
        readFiles();
    }

    public void showOptions() {
        int indexSelected;
        do {
            showMenu();
            indexSelected = scanner.nextInt();
            switchMenuOptions(indexSelected);
        } while (indexSelected != 9);

        // de aici inlocuiesc cu meniu
        passengers.showPassengers();

        passengers.updatePassenger(1, "Grigoras Victor", "15466546546", "ATH5542", 1);


        System.out.println();
        flight.showFlights();
    }

    private void showMenu() {
        System.out.println("***Menu***");
        System.out.println("1.Show Flights");
        System.out.println("2.Add Flight");
        System.out.println("3.Update Flight");
        System.out.println("4.Delete Flight");
        System.out.println("5.Show departures for one city");
        System.out.println("6.Show arrivals for one city");
        System.out.println("7.Show flights sorted by time");
        System.out.println("8.Reserve flight");
        System.out.println("9.Exit");
        System.out.print("Enter your chioce :");
    }

    private void switchMenuOptions(int index) {
        switch (index) {
            case 1 -> flight.showFlights();
            case 2 -> tryAddFlight();
            case 3 -> tryUpdateFlight();
            case 4 -> deleteFlight();
            case 5 -> showDeparturesByCity();
            case 6 -> showArrivalsByCity();
            case 7 -> flight.showFlightsOrderedByTime();
            case 8 -> reserveFlight();
            default -> System.out.println("Invalid option, please try again!");

        }
    }

    //TODO nu merge sa adaug corect

    private FlightDetails enterDetails() {
        FlightDetails flight = new FlightDetails();
        System.out.print("Enter plane code : ");
        String plane = scanner.nextLine();
        flight.setPlane(plane);
        System.out.print("Enter departure city : ");
        String departureCity = scanner.nextLine();
        flight.setDepartureCity(departureCity);
        System.out.print("Enter destination city : ");
        String destinationCity = scanner.nextLine();
        flight.setDestinationCity(destinationCity);
        System.out.print("Enter distance between the two cities : ");
        int distance = scanner.nextInt();
        flight.setDistance(distance);
        System.out.print("Enter average speed of the plane : ");
        int average = scanner.nextInt();
        flight.setAverageSpeed(average);
        int hour = enterHour();
        int minutes = enterMinutes();
        flight.setDepartureTime(hour,minutes);
        System.out.println("enter the number o seats : ");
        int seats = scanner.nextInt();
        flight.setAvailableSeats(seats);
        return flight;
    }
    private int enterHour(){
        System.out.print("Enter hour of departure : ");
        int hour = scanner.nextInt();
        try {
            this.flight.checkHour(hour);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
            enterHour();
        }
        return hour;
    }
    private int enterMinutes(){
        System.out.print("Enter the minute of departure : ");
        int minutes = scanner.nextInt();
        try {
            this.flight.checkMinutes(minutes);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
            enterMinutes();
        }
        return minutes;
    }

    private void showDeparturesByCity() {
        System.out.println("Enter city");
        String city = scanner.nextLine();
        flight.showCityDepartureFlights(city);
    }

    private void showArrivalsByCity() {
        System.out.println("Enter city");
        String city = scanner.nextLine();
        flight.showCityArrivalFlights(city);
    }

    private void tryAddFlight(String plane, String departureCity, String destinationCity, int distance, int average, int hour, int minutes, int seats) {
        try {
            flight.addFlight(plane, departureCity, destinationCity, distance, average, hour, minutes, seats);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void tryAddFlight() {
        FlightDetails flightDetails = enterDetails();
        flight.addFlight(flightDetails);
    }

    private void tryUpdateFlight() {
        flight.showFlights();
        System.out.println("Enter flight to update : ");
        int index = scanner.nextInt();
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            tryUpdateFlight();
        }
        FlightDetails flight = enterDetails();

        this.flight.updateFlight(index, flight);
    }

    private void deleteFlight() {
        flight.showFlights();
        System.out.print("Choose flight to delete : ");
        int index = scanner.nextInt();
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            deleteFlight();
        }
        flight.deleteFlight(index);
    }

    private void checkIndex(int index) throws WrongIndexException {
        if (index < 0 || index > flight.numberOfFlights()) {
            throw new WrongIndexException();
        }
    }

    private void reserveFlight() {
        flight.showFlights();
        System.out.println("Choose flight to reserve : ");
        int index =  scanner.nextInt();
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            reserveFlight();
        }
        String plane = flight.returnPlaneCode(index);
        int seats = flight.returnSeats(index);
        addPassengerDetails();
    }
    // TODO de impementat introducere pasager
    private void addPassengerDetails(){

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
                            Integer.parseInt(list.get(6)), Integer.parseInt(list.get(7)));
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
                            Integer.parseInt(list.get(3)), LocalDateTime.parse(list.get(4)));
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
