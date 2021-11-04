public class FlightDetails {
    private String plane;
    private String destinationCity;
    private String departureCity;
    private int distance;

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    private int averageSpeed;

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
                ", distance=" + distance +
                ", averageSpeed=" + averageSpeed + ", ";
    }
}
