package crud.board.BoardAuthority.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long postId;
    private String username;
    private String content;
}
