package crud.board.BoardAuthority.entity.authentication;

import crud.board.BoardAuthority.entity.thread.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Path {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String route;

    // Role
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "paths")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role){
        this.roles.add(role);
        role.getPaths().add(this);
    }
}
