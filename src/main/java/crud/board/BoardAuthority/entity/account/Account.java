package crud.board.BoardAuthority.entity.account;

import crud.board.BoardAuthority.entity.account.embeddables.AccountInform;
import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Embedded
    private AccountInform accountInform;


    // Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setRole(Role role) {
        if (this.role != null){
            this.role.getAccounts().remove(this);
        }
        this.role = role;
        role.getAccounts().add(this);
    }
}
