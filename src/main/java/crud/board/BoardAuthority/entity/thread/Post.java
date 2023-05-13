package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    private String title;
    private String content;

    private TimeInform timeInform;

    // Account
    // Thread
    // Comment
}
