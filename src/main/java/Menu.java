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
    Scanner scannerNumber = new Scanner(System.in);
    Scanner scannerText = new Scanner(System.in);

    public Menu() {
        readFiles();
    }

    public void showOptions() {
        int indexSelected;
        do {
            showMenu();
            indexSelected = scannerNumber.nextInt();
            switchMenuOptions(indexSelected);
        } while (indexSelected != 13);

        // de aici inlocuiesc cu meniu
//        passengers.showPassengers();
//        passengers.updatePassenger(1, "Grigoras Victor", "15466546546", "ATH5542", 1);

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
        System.out.println("9.Show my reservation");
        System.out.println("10.Show list with all pasengers");
        System.out.println("11.Update passanger name and id");
        System.out.println("12.Delete passenger ");
        System.out.println("13.Exit");
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
            case 9 -> showMyRerservation();
            case 10 ->passengers.showPassengers();
            case 11 ->updatePassenger();
            case 12 ->deletePassenger();
            case 13 -> System.out.println("Goodbye!");
            default -> System.out.println("Invalid option, please try again!");

        }
    }

    private void deletePassenger() {
        passengers.showPassengers();
        System.out.print("Choose option to delete : ");
        int index = scannerNumber.nextInt();
        passengers.deletePassenger(index);
    }

    private void updatePassenger() {
        passengers.showPassengers();
        System.out.print("Choose passanger to update :");
        int index = scannerNumber.nextInt();
        try {
            checkIndexPassengers(index);
        } catch (WrongIndexException e) {
            updatePassenger();
        }
        Passenger updatePassenger = new Passenger();
        System.out.print("Enter new name : ");
        updatePassenger.setName(scannerText.nextLine());
        System.out.print("Enter new Id : ");
        updatePassenger.setIdNumber(scannerText.nextLine());
        updatePassenger.setPlane(passengers.returnPlane(index));
        updatePassenger.setNumberOfSeats(passengers.returnSeats(index));
        updatePassenger.setDateOfAquisition(passengers.returnDate(index));
        passengers.updatePassenger(index,updatePassenger);

    }

    private void showMyRerservation() {
        System.out.print("Enter your name : ");
        String name = scannerText.nextLine();
        passengers.showReservation(name);
    }

    private FlightDetails enterDetails() {
        FlightDetails flight = new FlightDetails();
        System.out.print("Enter plane code : ");
        String plane = scannerText.nextLine();
        flight.setPlane(plane);
        System.out.print("Enter departure city : ");
        String departureCity = scannerText.nextLine();
        flight.setDepartureCity(departureCity);
        System.out.print("Enter destination city : ");
        String destinationCity = scannerText.nextLine();
        flight.setDestinationCity(destinationCity);
        System.out.print("Enter distance between the two cities : ");
        int distance = scannerNumber.nextInt();
        flight.setDistance(distance);
        System.out.print("Enter average speed of the plane : ");
        int average = scannerNumber.nextInt();
        flight.setAverageSpeed(average);
        int hour = enterHour();
        int minutes = enterMinutes();
        flight.setDepartureTime(hour, minutes);
        System.out.print("enter the number o seats : ");
        int seats = scannerNumber.nextInt();
        flight.setAvailableSeats(seats);
        return flight;
    }

    private int enterHour() {
        System.out.print("Enter hour of departure : ");
        int hour = scannerNumber.nextInt();
        try {
            this.flight.checkHour(hour);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
            enterHour();
        }
        return hour;
    }

    private int enterMinutes() {
        System.out.print("Enter the minute of departure : ");
        int minutes = scannerNumber.nextInt();
        try {
            this.flight.checkMinutes(minutes);
        } catch (WrongDateException e) {
            System.out.println(e.getMessage());
            enterMinutes();
        }
        return minutes;
    }

    private void showDeparturesByCity() {
        System.out.print("Enter city : ");
        String city = scannerText.nextLine();
        flight.showCityDepartureFlights(city);
    }

    private void showArrivalsByCity() {
        System.out.print("Enter city : ");
        String city = scannerText.nextLine();
        flight.showCityArrivalFlights(city);
    }

    private void tryAddFlight() {
        FlightDetails flightDetails = enterDetails();
        flight.addFlight(flightDetails);
    }

    private void tryUpdateFlight() {
        flight.showFlights();
        System.out.println("Enter flight to update : ");
        int index = scannerNumber.nextInt();
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
        int index = scannerNumber.nextInt();
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

    private void checkIndexPassengers(int index) throws WrongIndexException {
        if (index < 0 || index > passengers.returnNumberOfPassengers()) {
            throw new WrongIndexException();
        }
    }

    private void reserveFlight() {
        flight.showFlights();
        System.out.println("Choose flight to reserve : ");
        int index = scannerNumber.nextInt();
        index--;
        try {
            checkIndex(index);
        } catch (WrongIndexException e) {
            reserveFlight();
        }
        String plane = flight.returnPlaneCode(index);
        int seats = flight.returnSeats(index);
        if (checkSeats(seats)) {
            System.out.println("No seats available !");
            return;
        }
        addPassengerDetails(plane, seats, index);
    }

    private void addPassengerDetails(String plane, int seats, int index) {
        Passenger passenger = new Passenger();
        passenger.setPlane(plane);
        System.out.print("Enter entire name : ");
        passenger.setName(scannerText.nextLine());
        System.out.print("Enter Id number : ");
        passenger.setIdNumber(scannerText.nextLine());
        passenger.setDateOfAquisition(LocalDateTime.now());
        int desiredSeats = seatsReservation(seats);
        if (desiredSeats != 0) {
            passenger.setNumberOfSeats(desiredSeats);
            this.passengers.addPassenger(passenger);
            this.flight.decreaseSeats(index, desiredSeats);
        } else {
            System.out.println("Not enough seats available!");
            System.out.println("The reservation was cannceled , try again!");
        }
    }

    private int seatsReservation(int seats) {
        int desiredSeats;
        boolean checkIfShowMessage = false;
        do {
            if (checkIfShowMessage) {
                System.out.println("Invalid option, please try again !");
            }
            System.out.println("Available seats : " + seats);
            System.out.println("Enter number of seats that you want to reserve : ");
            desiredSeats = scannerNumber.nextInt();
            checkIfShowMessage = true;
        } while (desiredSeats <= 0);

        if (checkSeats(seats, desiredSeats)) {
            return desiredSeats;
        } else {
            return 0;
        }
    }

    private boolean checkSeats(int seats) {
        return seats <= 0;
    }
    private boolean checkSeats(int seats, int desiredSeats) {
        return seats > desiredSeats;
    }

    private void readFiles() {

        Runnable readFlights = () -> {
            File file = new File(flightsTxt);
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(","));
                    FlightDetails currentFlight = new FlightDetails();
                    currentFlight.setPlane(list.get(0));
                    currentFlight.setDepartureCity(list.get(1));
                    currentFlight.setDestinationCity(list.get(2));
                    currentFlight.setDistance(Integer.parseInt(list.get(3)));
                    currentFlight.setAverageSpeed(Integer.parseInt(list.get(4)));
                    currentFlight.setDepartureTime(Integer.parseInt(list.get(5)), Integer.parseInt(list.get(6)));
                    currentFlight.setAvailableSeats(Integer.parseInt(list.get(7)));
                    flight.addFlight(currentFlight);
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
