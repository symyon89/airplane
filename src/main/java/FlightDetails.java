public class FlightDetails {
    private String plane;
    private String city;
    private int distance;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
                ", city='" + city + '\'' +
                ", distance=" + distance +
                ", averageSpeed=" + averageSpeed;
    }
}
