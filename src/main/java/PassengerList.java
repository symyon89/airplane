
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

    public void addPassenger(Passenger passenger){
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

    public void showReservation(String name){
        if(listOfPassengers.isEmpty())
            System.out.println("List of passengers is empty");
        else {
            AtomicInteger index = new AtomicInteger();
            index.set(1);
            listOfPassengers.stream()
                    .filter(passenger -> passenger.getName().equalsIgnoreCase(name))
                    .forEach(passenger -> {
                        System.out.println(index + "." + passenger);
                        index.incrementAndGet();
                    });
            if(index.get() == 1){
                System.out.println("No reservation was found");
            }

        }

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

    public void updatePassenger (int index,Passenger passenger) {
        if (listOfPassengers.isEmpty()) {
            System.out.println("List of passengers is empty");
            return;
        }
        index--;
        listOfPassengers.remove(index);
        listOfPassengers.add(index,passenger);
        updateFilePassengers();
    }

    public String returnPlane(int index) {
        return listOfPassengers.get(index).getPlane();
    }

    public int returnSeats(int index) {
        return listOfPassengers.get(index).getNumberOfSeats();
    }

    public int returnNumberOfPassengers() {
        return listOfPassengers.size();
    }

    public void deletePassenger(int index) {
        if (listOfPassengers.isEmpty()) {
            System.out.println("List of passengers is empty");
            return;
        }
        index--;
        listOfPassengers.remove(index);
        updateFilePassengers();
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
