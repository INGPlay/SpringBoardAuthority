package crud.board.BoardAuthority.domain.response.pagingPost;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagingInform {

    private int startNumber;
    private int endNumber;
    private boolean hasBefore;
    private boolean hasNext;
}
