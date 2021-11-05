import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Passenger {
    private String name;
    private String idNumber;
    private String plane;
    private int numberOfSeats;
    private LocalDateTime dateOfAquisition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getDateOfAquisition() {
        return dateOfAquisition;
    }

    public void setDateOfAquisition(LocalDateTime dateOfAquisition) {
        this.dateOfAquisition = dateOfAquisition;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = dateOfAquisition.format(formatter);

        return "Passenger :" +
                "name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", plane='" + plane + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", dateOfAquisition=" + dateFormatted;
    }
}
