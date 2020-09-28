package develop.bluedot.server.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserApiResponse {

    private Long id;

    private String email;

    private String name;

    private Integer genre;

    private String img;

    private Integer followingCount;

    private Integer followedCount;

    private Integer isArtist;

    private String bannerImg;


}