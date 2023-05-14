package crud.board.BoardAuthority.entity.account;

import crud.board.BoardAuthority.entity.thread.Comment;
import crud.board.BoardAuthority.entity.thread.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class AccountActivity {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountactivity_id")
    private Long id;

    // Post
    @OneToMany(mappedBy = "accountActivity")
    private List<Post> posts = new ArrayList<>();

    // Comment
    @OneToMany(mappedBy = "accountActivity")
    private List<Comment> comments = new ArrayList<>();

    // Account
    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
