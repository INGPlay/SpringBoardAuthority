package crud.board.BoardAuthority.domain.response;

import crud.board.BoardAuthority.domain.response.pagingPost.PagingInform;
import crud.board.BoardAuthority.domain.response.pagingPost.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PagingPostResponse {
    private PagingInform pagingInform;
    private List<PostResponse> postResponses;
}
