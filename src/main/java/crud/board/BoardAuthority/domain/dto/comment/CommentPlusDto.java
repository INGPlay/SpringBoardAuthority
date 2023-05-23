package crud.board.BoardAuthority.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentPlusDto {
    private Long commentGroup;
    private Long commentDepth;
}
