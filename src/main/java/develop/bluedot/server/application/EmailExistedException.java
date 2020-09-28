package develop.bluedot.server.application;

public class EmailExistedException extends RuntimeException {
    public EmailExistedException(String email){
        super("Email is already registerd: " + email);
    }

}
