package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Thread {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String Title;

    @Column
    private TimeInform timeInform;

    // Role 제한
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Post
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "thread")
    private List<Post> posts = new ArrayList<>();

    public void setRole(Role role) {
        if (this.role != null){
            role.getThread().remove(this);
        }
        this.role = role;
        role.getThread().add(this);
    }

    public void addPost(Post post){
        post.setThread(this);
        this.posts.add(post);
    }
}
