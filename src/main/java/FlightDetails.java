import java.time.LocalTime;

public class FlightDetails {
    private String plane;
    private String destinationCity;
    private String departureCity;
    private int distance;
    private int averageSpeed;
    LocalTime departureTime;
    private int availableSeats;


    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setDepartureTime(int hour, int minutes ) {
        departureTime =LocalTime.of(hour,minutes);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Flight : " +
                "plane='" + plane + '\'' +
                ", Departure city='" + departureCity + '\'' +
                ", Destination city='" + destinationCity + '\'' +
                ", Deprature Time='" + departureTime + '\'' +
                ", distance=" + distance + " Km" +
                ", averageSpeed=" + averageSpeed + ", " +
                ", available Seats =" + availableSeats + ", ";
    }
}
