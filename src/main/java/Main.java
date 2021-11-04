public class Main {
    public static void main(String[] args) {
        Flights flight = new Flights();
        flight.addFlight("ATH5544","New York","Bucuresti",10000,600,12,33);
        flight.addFlight("ATH5543","Paris","Brasov",500,500,14,22);
        flight.addFlight("ATH5542","Paris","Bucuresti",2700,550,23,0);
        flight.showFlights();
        flight.deleteFlight(2);
        flight.showFlights();
    }
}
