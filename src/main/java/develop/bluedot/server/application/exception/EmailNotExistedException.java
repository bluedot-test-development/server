package develop.bluedot.server.application.exception;

public class EmailNotExistedException extends RuntimeException {
    public EmailNotExistedException(String email){
        super("Email is not registerd: " + email);
    }

}
