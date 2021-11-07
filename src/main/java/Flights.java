import Exceptions.WrongDateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
    public void addFlight(FlightDetails flight){

    }
    public void showFlights() {
        if(flights.isEmpty())
            System.out.println("No available flights to show");
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
    public void showFlightsOrderedByTime(){
        List<FlightDetails> showOrderdFlights = flights;
        Comparator<FlightDetails> comparator = Comparator.comparing(FlightDetails::getDepartureTime);
        showOrderdFlights.sort(comparator);
        AtomicInteger index = new AtomicInteger();
        index.set(1);
        showOrderdFlights.forEach(thisFlight -> {
            TravelTime travelHours = (dist, avg) -> (dist / avg );
            TravelTime travelminutes = (dist, avg) -> (int)(((dist / (avg * 1.0))-((dist / avg ))) * 60) ;
            System.out.println(index + "." + thisFlight + "Travel Time = " +
                    travelHours.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                    " hours " + travelminutes.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed()) + " minutes");
            index.getAndIncrement();
        });
    }
    public void showCityArrivalFlights(String city) {
        if(flights.isEmpty())
            System.out.println("No available flights to show");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            flights.stream().filter(thisFlight -> thisFlight.getDestinationCity().equalsIgnoreCase(city))
                    .forEach(thisFlight -> {
                TravelTime travelHours = (dist, avg) -> (dist / avg );
                TravelTime travelminutes = (dist, avg) -> (int)(((dist / (avg * 1.0))-((dist / avg ))) * 60) ;
                System.out.println(index + "." + thisFlight + "Travel Time = " +
                        travelHours.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                        " hours " + travelminutes.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed()) + " minutes");
                index.getAndIncrement();
            });
            if (index.get() == 1)
                System.out.println("No arrivals found for this city");
        }
    }
    public void showCityDepartureFlights(String city) {
        if(flights.isEmpty())
            System.out.println("No available flights to show");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            flights.stream().filter(thisFlight -> thisFlight.getDepartureCity().equalsIgnoreCase(city))
                    .forEach(thisFlight -> {
                        TravelTime travelHours = (dist, avg) -> (dist / avg );
                        TravelTime travelminutes = (dist, avg) -> (int)(((dist / (avg * 1.0))-((dist / avg ))) * 60) ;
                        System.out.println(index + "." + thisFlight + "Travel Time = " +
                                travelHours.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                                " hours " + travelminutes.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed()) + " minutes");
                        index.getAndIncrement();
                    });
            if (index.get() == 1)
                System.out.println("No departures found for this city");
        }
    }
    public String returnPlaneCode(int index){
        return flights.get(index).getPlane();
    }

    public int returnSeats(int index){
        return flights.get(index).getAvailableSeats();
    }
    public void updateFlight (int index,String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes, int seats) throws WrongDateException {
        if (flights.isEmpty()) {
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
    public void updateFlight (int index,FlightDetails flight) {
        if (flights.isEmpty()) {
            System.out.println("No available flights to update");
            return;
        }
        index--;

        flights.add(index,flight);
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
    public int numberOfFlights(){
        return flights.size();
    }
}
