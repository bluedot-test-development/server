package develop.bluedot.server.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(exclude = {"user"})
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int followingId;

    @ManyToOne
    private User user;
}
