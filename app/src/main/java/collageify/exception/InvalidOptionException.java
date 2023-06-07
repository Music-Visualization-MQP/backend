package collageify.exception;

public class InvalidOptionException extends Exception {
    public InvalidOptionException(int option){
        super("the option, " + option + "is not a valid option");
    }
}
