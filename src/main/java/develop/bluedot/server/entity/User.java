package develop.bluedot.server.entity;

<<<<<<< Updated upstream
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> Stashed changes
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"postList","dotVideoList","relationList"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=User.class)
public class User {

    /**
     * 회원가입 정보 : 이메일, 이름, 비밀번호, 장르 test
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private Integer genre;

    private String img;

    private Integer isArtist;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Post> postList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference
    private List<DotVideo> dotVideoList;

<<<<<<< HEAD

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
<<<<<<< Updated upstream
    private List<Post> posts = new ArrayList<>();
=======
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Relation> relationList;
>>>>>>> 209e400353a4cfb7969f2507bf40841d6de5b4b6
=======
    private List<Post> posts;
>>>>>>> Stashed changes

}

