
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PassengerList {
    private final List<Passenger> listOfPassengers = new ArrayList<>();
    private static final String fileOfPassengers = "src/main/resources/passengers.txt";

    public void addPassenger(String name,String idNumber,String plane,int numberOfSeats){
        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setIdNumber(idNumber);
        passenger.setPlane(plane);
        passenger.setNumberOfSeats(numberOfSeats);
        passenger.setDateOfAquisition(LocalDateTime.now());
        listOfPassengers.add(passenger);
        updateFilePassengers();
    }
    public void addPassenger(String name, String idNumber, String plane, int numberOfSeats, LocalDateTime time) {
        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setIdNumber(idNumber);
        passenger.setPlane(plane);
        passenger.setNumberOfSeats(numberOfSeats);
        passenger.setDateOfAquisition(time);
        listOfPassengers.add(passenger);
        updateFilePassengers();
    }
    public void showPassengers(){
        if(listOfPassengers.isEmpty())
            System.out.println("List of passengers is empty");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            listOfPassengers.forEach(thisPassenger -> {
                System.out.println(index + "." + thisPassenger );
                index.getAndIncrement();
            });
        }
    }
    public void updatePassenger (int index,String name,String idNumber,String plane,int numberOfSeats) {
        if (listOfPassengers.isEmpty()) {
            System.out.println("List of passengers is empty");
            return;
        }
        index--;
        Passenger updatePassenger = listOfPassengers.get(index);
        updatePassenger.setName(name);
        updatePassenger.setIdNumber(idNumber);
        updatePassenger.setPlane(plane);
        updatePassenger.setNumberOfSeats(numberOfSeats);
        listOfPassengers.remove(index);
        listOfPassengers.add(index,updatePassenger);
        updateFilePassengers();
    }
    public void deletePassenger(int index) {
        if (listOfPassengers.isEmpty()) {
            System.out.println("List of passengers is empty");
            return;
        }
        index--;
        listOfPassengers.remove(index);
    }
    private void updateFilePassengers(){
        Runnable savePassenger = () ->{
            File file = new File(fileOfPassengers);
            try (FileWriter fileWriter = new FileWriter(file)) {
                final StringBuilder stringBuilder = new StringBuilder();
                listOfPassengers.forEach(passenger -> stringBuilder.append(passenger.getName()).append(",")
                        .append(passenger.getIdNumber()).append(",")
                        .append(passenger.getPlane()).append(",")
                        .append(passenger.getNumberOfSeats()).append(",")
                        .append(passenger.getDateOfAquisition()).append("\n"));
                fileWriter.write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread savePassengersThread = new Thread(savePassenger);
        savePassengersThread.start();
    }


}
