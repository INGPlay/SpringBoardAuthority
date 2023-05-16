package crud.board.BoardAuthority.entity.thread;

import com.fasterxml.jackson.annotation.JsonIgnore;
import crud.board.BoardAuthority.entity.authentication.Path;
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

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Setter(value = AccessLevel.NONE)
    @Column(nullable = false)
    private Long postSeqence = 0L;

    @Column
    private TimeInform timeInform;

    // Path
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    // Post
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "thread")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        post.setThread(this);
        this.posts.add(post);
    }

    public void updatePostSequence(){
        postSeqence += 1L;
    }
}
