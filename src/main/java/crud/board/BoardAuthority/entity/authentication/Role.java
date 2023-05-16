package crud.board.BoardAuthority.entity.authentication;

import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.thread.Thread;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Role {

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
    @ManyToMany
    @JoinTable(
            name = "Role_Path",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "path_id")
    )
    private Set<Path> paths = new HashSet<>();

    public void addAccounts(Account account){
        account.setRole(this);
        this.accounts.add(account);
    }

    public void addPath(Path path){
        this.paths.add(path);
        path.getRoles().add(this);
    }
}
