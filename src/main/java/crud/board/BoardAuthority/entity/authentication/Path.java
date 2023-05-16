package crud.board.BoardAuthority.entity.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.entity.thread.Thread;
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

    // Thread
    @OneToOne(mappedBy = "path", fetch = FetchType.LAZY)
    private Thread thread;

    // Post
    @OneToOne(mappedBy = "path", fetch = FetchType.LAZY)
    private Post post;

    public void addRole(Role role){
        this.roles.add(role);
        role.getPaths().add(this);
    }
}
