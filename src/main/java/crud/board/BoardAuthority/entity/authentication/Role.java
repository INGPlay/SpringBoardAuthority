package crud.board.BoardAuthority.entity.authentication;

import crud.board.BoardAuthority.entity.authentication.enums.AccountRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private AccountRole accountRole;
    // Account
    // Path
}
