public class Main {
    public static void main(String[] args) {
        Flights flight = new Flights();
        flight.addFlight("ATH5544","Islanda",2500,600);
        flight.addFlight("ATH5543","Islanda",2500,600);
        flight.addFlight("ATH5542","Islanda",2500,600);
        flight.showFlights();
        flight.deleteFlight(2);
        flight.showFlights();
    }
}
