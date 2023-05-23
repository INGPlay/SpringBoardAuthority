package crud.board.BoardAuthority.domain.response.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private String username;
    private String content;
    private Date createdTime;
    private Date updatedTime;
    private Long commentGroup;
    private Long commentDepth;
}
