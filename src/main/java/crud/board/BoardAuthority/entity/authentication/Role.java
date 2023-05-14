package crud.board.BoardAuthority.entity.authentication;

import crud.board.BoardAuthority.entity.authentication.enums.AccountRole;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.thread.Thread;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Role {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    // Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Thread
    @OneToMany(mappedBy = "role")
    private List<Thread> thread = new ArrayList<>();

    // Path
    @ManyToMany
    @JoinTable(
            name = "Role_Path",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "path_id")
    )
    private Set<Path> paths = new HashSet<>();
}
