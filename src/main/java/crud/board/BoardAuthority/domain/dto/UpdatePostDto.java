package crud.board.BoardAuthority.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UpdatePostDto {
    private Long postId;
    private String title;
    private String content;
}
