package develop.bluedot.server.application;


public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(){
        super("Password is wrong");
    }
}
