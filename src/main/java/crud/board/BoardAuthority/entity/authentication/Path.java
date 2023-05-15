package crud.board.BoardAuthority.entity.authentication;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long id;

    @Column
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
