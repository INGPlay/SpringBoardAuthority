package crud.board.BoardAuthority.entity.thread;

import crud.board.BoardAuthority.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Thread {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    private String Title;

    private TimeInform timeInform;

    // User
}
