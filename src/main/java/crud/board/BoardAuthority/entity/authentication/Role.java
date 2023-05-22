package crud.board.BoardAuthority.entity.authentication;

import crud.board.BoardAuthority.entity.account.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Entity
@Getter @Setter
@NoArgsConstructor
public class Role {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String ROLE_ADMIN = "ROLE_ADMIN";

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Column(unique = true, nullable = false)
    private String roleName;

    // Account
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "role")
    private List<Account> accounts = new ArrayList<>();

    // Path
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Role_Path",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "path_id")
    )
    private Set<Path> paths = new HashSet<>();

    public void setPaths(Set<Path> paths) {
        if (this.roleName.equals(ROLE_ADMIN)){
            log.info("어드민 권한 경로를 변경할 수 없습니다.");
            return;
        }

        paths.stream().forEach(p ->
                p.addRole(this)
        );
        this.paths = paths;
    }

    public void addAccounts(Account account){
        account.setRole(this);
        this.accounts.add(account);
    }

    public void addPath(Path path){
        if (this.roleName.equals(ROLE_ADMIN)){
            log.info("어드민 권한 경로를 변경할 수 없습니다.");
            return;
        }

        this.paths.add(path);
        path.getRoles().add(this);
    }
}
