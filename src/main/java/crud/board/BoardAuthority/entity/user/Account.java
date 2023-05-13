package crud.board.BoardAuthority.entity.user;

import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Embedded
    private TimeInform timeInform;

    // Post
    // Role
}
