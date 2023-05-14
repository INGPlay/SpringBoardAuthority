package crud.board.BoardAuthority.entity.thread;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CommentPlus {

    @Setter(value = AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentPlus_id")
    private Long id;

    // 그룹
    @Column
    private Long commentGroup;

    // 그룹 내 위에서 아래로의 순서
    @Column
    private Long commentOrder;

    // 그룹 내 왼쪽에서 오른쪽으로의 순서
    @Column
    private Long commentDepth;

    // Comment
    @MapsId
    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
