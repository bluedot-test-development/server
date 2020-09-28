package develop.bluedot.server.network.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionApiResponse {

    private String accessToken;

}
