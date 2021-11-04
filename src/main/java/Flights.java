import Exceptions.WrongDateException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Flights {
    List<FlightDetails> flights = new ArrayList<>();

    public void addFlight  (String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes) throws WrongDateException {
        FlightDetails flight = new FlightDetails();
        flight.setPlane(plane);
        flight.setDepartureCity(departureCity);
        flight.setDestinationCity(destinationCity);
        flight.setDistance(distance);
        flight.setAverageSpeed(average);
        checkHour(hour);
        checkMinutes(minutes);
        flight.setDepartureTime(hour,minutes);
        flights.add(flight);
    }
    public void showFlights() {
        if(flights.isEmpty())
            System.out.println("No available flights");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            flights.forEach(thisFlight -> {
                TravelTime travelHours = (dist, avg) -> dist / (avg * 1.0 );
                TravelTime travelminutes = (dist, avg) -> dist / (avg * 1.0);
                System.out.println(index + "." + thisFlight + "Travel Time = " +
                        (int)travelHours.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                        " hours " + travelminutes.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed()) + " minutes");
                index.getAndIncrement();
            });
        }
    }
    public void updateFlight (int index,String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes) throws WrongDateException {
        if (flights.isEmpty()) {
            System.out.println("No available flights");
            return;
        }
       index--;
       FlightDetails updateFlight = flights.get(index);
        updateFlight.setPlane(plane);
        updateFlight.setDepartureCity(departureCity);
        updateFlight.setDestinationCity(destinationCity);
        updateFlight.setDistance(distance);
        updateFlight.setAverageSpeed(average);
        checkHour(hour);
        checkMinutes(minutes);
        updateFlight.setDepartureTime(hour,minutes);
        flights.remove(index);
        flights.add(index,updateFlight);
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
}
