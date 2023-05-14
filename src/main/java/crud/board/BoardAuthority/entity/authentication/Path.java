package crud.board.BoardAuthority.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Path {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long id;

    @Column
    private String route;

    // Role
    @ManyToMany(mappedBy = "paths")
    private Set<Role> roles = new HashSet<>();
}
