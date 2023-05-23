package crud.board.BoardAuthority.domain.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubmitCommentDto {
    private Long postId;
    private String username;
    private String content;
    private Long commentGroup;
    private CommentPlusStatus commentPlusStatus;
}
