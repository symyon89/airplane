import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Flights {
    List<FlightDetails> flights = new ArrayList<>();

    public void addFlight(String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes){
        FlightDetails flight = new FlightDetails();
        flight.setPlane(plane);
        flight.setDepartureCity(departureCity);
        flight.setDestinationCity(destinationCity);
        flight.setDistance(distance);
        flight.setAverageSpeed(average);
        flight.setDepartureTime(hour,minutes);
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
                index.getAndIncrement();
            });
        }
    }
    public void updateFlight (int index,String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes){
        if (flights.isEmpty()){
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
        updateFlight.setDepartureTime(hour,minutes);
        flights.remove(index);
        flights.add(index,updateFlight);
    }
    public void deleteFlight(int index){
        if (flights.isEmpty()){
            System.out.println("No available flights");
            return;
        }
        index--;
        flights.remove(index);
    }
}
