package crud.board.BoardAuthority.domain.response.pagingPost;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagingInform {

    private List<Integer> pageIndexList;
    private List<String> pageRouteList;
    private boolean hasBefore;
    private boolean hasNext;
}
