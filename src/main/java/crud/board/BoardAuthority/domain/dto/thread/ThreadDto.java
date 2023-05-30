package crud.board.BoardAuthority.domain.dto.thread;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ThreadDto {
    private String username;
    private String title;
    private String content;
}
