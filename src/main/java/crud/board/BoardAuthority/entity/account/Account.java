package crud.board.BoardAuthority.entity.account;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Account {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 계정 생성 및 수정시간
    @Embedded
    private TimeInform timeInform;

    // Role
    @OneToMany(mappedBy = "account")
    private List<Role> roles = new ArrayList<>();

    // AccountActivity
    @OneToOne(mappedBy = "account")
    private AccountActivity accountActivity;
}
