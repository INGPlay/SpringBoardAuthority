package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.account.AccountActivity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String content;

    @Column
    private TimeInform timeInform;

    @OneToOne(mappedBy = "comment")
    private CommentPlus commentPlus;

    // Post
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountActivity accountActivity;
}
