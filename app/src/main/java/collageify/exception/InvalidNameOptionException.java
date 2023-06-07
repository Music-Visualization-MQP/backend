package collageify.exception;

public class InvalidNameOptionException extends Exception {
    public InvalidNameOptionException(int option){
        super("the option, " + option + "is not a valid option");
    }
}
