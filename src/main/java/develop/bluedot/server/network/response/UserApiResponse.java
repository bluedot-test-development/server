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
<<<<<<< HEAD

    private String email;

    private String name;
=======
>>>>>>> 209e400353a4cfb7969f2507bf40841d6de5b4b6

    private Integer genre;

    private String name;

    private String img;

    private String genre;

}