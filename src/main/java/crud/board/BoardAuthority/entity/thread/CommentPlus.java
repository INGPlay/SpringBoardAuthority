package crud.board.BoardAuthority.entity.thread;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Entity
//@Getter @Setter
//@NoArgsConstructor
@Embeddable
@Getter @Setter
public class CommentPlus {

    // 그룹
    @Column
    private Long commentGroup;

    // 그룹 내 왼쪽에서 오른쪽으로의 순서
    @Column
    private Long commentDepth;
}
