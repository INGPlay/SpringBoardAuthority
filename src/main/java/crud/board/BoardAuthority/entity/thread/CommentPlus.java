package crud.board.BoardAuthority.entity.thread;

import javax.persistence.*;

@Entity
public class CommentPlus {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentplus_id")
    private Long id;

    // Comment
}
