package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.authentication.Path;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column
    private TimeInform timeInform;

    // Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // Comment
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public void setAccount(Account account) {
        if (this.account != null){
            account.getAccountInform().getPosts().remove(this);
        }
        this.account = account;
        account.getAccountInform().getPosts().add(this);
    }

    public void addComment(Comment comment){
        comment.setPost(this);
        this.comments.add(comment);
    }
}
