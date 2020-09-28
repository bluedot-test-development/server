package develop.bluedot.server.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String genre;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Post> postList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference
    private List<DotVideo> dotVideoList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Relation> relationList;

}

