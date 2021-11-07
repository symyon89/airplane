package Exceptions;

public class WrongDateException extends Exception{
    public WrongDateException() {
        super("Invalid date, try again!");
    }
}
