package crud.board.BoardAuthority.entity.account;

import crud.board.BoardAuthority.entity.account.embeddables.AccountInform;
import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@NoArgsConstructor
@Getter @Setter
public class Account {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String ROLE_ADMIN = "ROLE_ADMIN";

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public void setRole(Role role) {

        if (this.role != null){

            // 어드민 계정은 변경될 수 없다.
            if (this.role.getRoleName().equals(ROLE_ADMIN)){
                log.info("어드민의 권한을 변경할 수 없습니다.");
                return;
            }

            this.role.getAccounts().remove(this);
        }
        this.role = role;
        role.getAccounts().add(this);
    }
}
