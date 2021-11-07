package Exceptions;

public class WrongIndexException extends Exception{
    public WrongIndexException() {
        super("Wrong index, please try again");
    }
}
