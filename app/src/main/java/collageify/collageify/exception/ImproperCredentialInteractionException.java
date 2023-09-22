package collageify.collageify.exception;

public class ImproperCredentialInteractionException extends Exception {

    ImproperCredentialInteractionException(String s){
        super(s);
        System.out.println("Why are you trying to access something irrelevant");
    }
}
