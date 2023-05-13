package crud.board.BoardAuthority.entity.thread;

import javax.persistence.*;

@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String comment;

    // User

}
