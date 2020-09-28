package develop.bluedot.server.application;

import lombok.Data;

@Data
public class SessionRequestDto {

    private String email;
    private String password;
}
