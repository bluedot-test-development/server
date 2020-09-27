package develop.bluedot.server.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Enumerated(EnumType.STRING)
    private String status;

    private String password;

    private String phoneNumber;

    private String email;

    private String account;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;
}

