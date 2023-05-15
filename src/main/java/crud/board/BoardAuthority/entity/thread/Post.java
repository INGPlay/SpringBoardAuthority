package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.account.Account;
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
public class Post {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private TimeInform timeInform;

    // Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Thread
    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

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

    public void setThread(Thread thread) {
        if (this.thread != null){
            thread.getPosts().remove(this);
        }
        this.thread = thread;
        thread.getPosts().add(this);
    }

    public void addComment(Comment comment){
        comment.setPost(this);
        this.comments.add(comment);
    }
}
