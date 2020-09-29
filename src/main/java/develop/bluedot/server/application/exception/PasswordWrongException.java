package develop.bluedot.server.application.exception;


public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(){
        super("Password is wrong");
    }
}
