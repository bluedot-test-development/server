package develop.bluedot.server.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {


    private Long id;

    private String email;

    private String name;

    private String password;

    private Integer genre;

    private Integer isArtist;

    private String img;



}