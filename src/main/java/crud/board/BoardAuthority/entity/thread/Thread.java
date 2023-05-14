package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Thread {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    @Column
    private String Title;

    @Column
    private TimeInform timeInform;

    // Role 제한
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Post
    @OneToMany(mappedBy = "thread")
    private List<Post> posts = new ArrayList<>();
}
