package crud.board.BoardAuthority.domain.dto;

import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private String username;
    private TimeInform timeInform;
}
