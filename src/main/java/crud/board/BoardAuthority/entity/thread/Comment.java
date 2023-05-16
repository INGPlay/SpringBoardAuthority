package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String content;

    @Column
    private TimeInform timeInform;

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY)
    private CommentPlus commentPlus;

    // Post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public void setCommentPlus(CommentPlus commentPlus) {
        this.commentPlus = commentPlus;
        commentPlus.setComment(this);
    }

    public void setPost(Post post) {
        if (this.post != null){
            post.getComments().remove(this);
        }
        this.post = post;
        post.getComments().add(this);
    }

    public void setAccount(Account account) {
        if (this.account != null){
            account.getAccountInform().getComments().remove(this);
        }
        this.account = account;
        account.getAccountInform().getComments().add(this);
    }
}
