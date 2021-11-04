package Exceptions;

public class WrongDateException extends Exception{
    public WrongDateException() {
        super("Invalid date, the flight was not added");
    }
}
