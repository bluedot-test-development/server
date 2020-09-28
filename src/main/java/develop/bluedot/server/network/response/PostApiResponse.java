package develop.bluedot.server.network.response;

import develop.bluedot.server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostApiResponse {

    private String userName;

    private String userImg;

    private Integer userGenre;

    private String description;

    private String img;

    private String title;
}
