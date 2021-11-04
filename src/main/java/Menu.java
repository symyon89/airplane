import Exceptions.WrongDateException;


public class Menu {

    public void showMenu(){
        Flights flight = new Flights();
        flight.showFlights();

        tryAddFlight(flight,"ATH5544","New York","Bucuresti",10000,600,11,99);
        tryAddFlight(flight,"ATH5543","Paris","Brasov",500,500,14,22);
        tryAddFlight(flight,"ATH5542","Paris","Bucuresti",2700,550,23,0);

        flight.showFlights();
        System.out.println();
        flight.deleteFlight(2);
        flight.showFlights();

        tryupdateFlight(flight,1,"ATH55A","London","Bucuresti",3700,550,22,15);

        System.out.println();
        flight.showFlights();
    }
    public void tryAddFlight(Flights flight, String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes) {
        try {
            flight.addFlight(plane,departureCity,destinationCity,distance,average,hour,minutes);
        } catch (WrongDateException e) {
            e.getMessage();
        }
    }
    public void tryupdateFlight(Flights flight,int index, String plane,String departureCity,String destinationCity, int distance, int average,int hour,int minutes) {
        try {
            flight.updateFlight(index,plane,departureCity,destinationCity,distance,average,hour,minutes);
        } catch (WrongDateException e) {
            e.getMessage();
        }
    }
}
