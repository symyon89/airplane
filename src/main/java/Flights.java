import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Flights {
    List<FlightDetails> flights = new ArrayList<>();

    public void addFlight(String plane,String departureCity,String destinationCity, int distance, int average){
        FlightDetails flight = new FlightDetails();
        flight.setPlane(plane);
        flight.setDepartureCity(departureCity);
        flight.setDestinationCity(destinationCity);
        flight.setDistance(distance);
        flight.setAverageSpeed(average);
        flights.add(flight);
    }
    public void showFlights(){
        if(flights.isEmpty())
            System.out.println("No available flights");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            flights.forEach(thisFlight -> {
                TravelTime travelTime = (dist, avg) -> dist / avg * 1.0;
                System.out.println(index + "." + thisFlight + "Travel Time = " +
                        travelTime.calculateTime(thisFlight.getDistance(), thisFlight.getAverageSpeed())+
                        " hours");
                index.set(index.get() + 1);
            });
        }
    }
    public void deleteFlight(int index){
        index--;
        flights.remove(index);
    }
}
