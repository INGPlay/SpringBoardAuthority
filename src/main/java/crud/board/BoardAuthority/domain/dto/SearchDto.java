package crud.board.BoardAuthority.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchDto {
    private String searchWord;
    private String searchOption;
}
