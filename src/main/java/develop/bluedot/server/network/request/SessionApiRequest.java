package develop.bluedot.server.network.request;

import lombok.Data;

@Data
public class SessionApiRequest {

    private String email;
    private String password;
}
