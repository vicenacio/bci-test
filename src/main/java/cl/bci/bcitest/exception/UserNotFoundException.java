package cl.bci.bcitest.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String message){
        super(message);
    }

}
