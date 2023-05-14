package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.account.AccountActivity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
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
    private AccountActivity accountActivity;

    // Thread
    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

    // Comment
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
