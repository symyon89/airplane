import Exceptions.WrongDateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Flights {
    private static final String flightsTxt = "src/main/resources/flights.txt";
    private final List<FlightDetails> flights = new ArrayList<>();

    public void addFlight  (String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes, int seats) throws WrongDateException {
        FlightDetails flight = new FlightDetails();
        flight.setPlane(plane);
        flight.setDepartureCity(departureCity);
        flight.setDestinationCity(destinationCity);
        flight.setDistance(distance);
        flight.setAverageSpeed(average);
        checkHour(hour);
        checkMinutes(minutes);
        flight.setDepartureTime(hour,minutes);
        flight.setAvailableSeats(seats);
        flights.add(flight);
        updateFileFlights();
    }
    public void showFlights() {
        if(flights.size() == 0)
            System.out.println("No available flights to add");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            flights.forEach(thisFlight -> {
                TravelTime travelHours = (dist, avg) -> (dist / avg );
                TravelTime travelminutes = (dist, avg) -> (int)(((dist / (avg * 1.0))-((dist / avg ))) * 60) ;
                System.out.println(index + "." + thisFlight + "Travel Time = " +
                        travelHours.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                        " hours " + travelminutes.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed()) + " minutes");
                index.getAndIncrement();
            });
        }
    }
    public void updateFlight (int index,String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes, int seats) throws WrongDateException {
        if (flights.size() == 0) {
            System.out.println("No available flights to update");
            return;
        }
       index--;
       FlightDetails updateFlight = flights.get(index);
        updateFlight.setPlane(plane);
        updateFlight.setDepartureCity(departureCity);
        updateFlight.setDestinationCity(destinationCity);
        updateFlight.setDistance(distance);
        updateFlight.setAverageSpeed(average);
        updateFlight.setAvailableSeats(seats);
        checkHour(hour);
        checkMinutes(minutes);
        updateFlight.setDepartureTime(hour,minutes);
        flights.remove(index);
        flights.add(index,updateFlight);
        updateFileFlights();
    }
    public void deleteFlight(int index) {
        if (flights.isEmpty()) {
            System.out.println("No available flights");
            return;
        }
        index--;
        flights.remove(index);
    }
    public void checkHour(int hour) throws WrongDateException{
        if(hour < 0 || hour > 23){
            throw new WrongDateException();
        }
    }
    public void checkMinutes(int minutes) throws WrongDateException{
        if (minutes < 0 || minutes >59) {
            throw new WrongDateException();
        }
    }
    private void updateFileFlights(){
        Runnable saveFlights = () ->{
            File file = new File(flightsTxt);
            try (FileWriter fileWriter = new FileWriter(file)) {
               final StringBuilder stringBuilder = new StringBuilder();
               flights.forEach(flight -> stringBuilder.append(flight.getPlane()).append(",")
                       .append(flight.getDepartureCity()).append(",")
                       .append(flight.getDestinationCity()).append(",")
                       .append(flight.getDistance()).append(",")
                       .append(flight.getAverageSpeed()).append(",")
                       .append(flight.getDepartureTime().getHour()).append(",")
                       .append(flight.getDepartureTime().getMinute()).append(",")
                       .append(flight.getAvailableSeats()).append("\n"));
               fileWriter.write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread saveFlightsThread = new Thread(saveFlights);
        saveFlightsThread.start();
    }
}
