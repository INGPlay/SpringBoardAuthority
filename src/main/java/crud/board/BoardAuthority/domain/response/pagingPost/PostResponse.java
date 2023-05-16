package crud.board.BoardAuthority.domain.response.pagingPost;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class PostResponse {

    private String username;
    private String title;
    private String content;
    private Date createdTime;
    private Date updatedTime;
    private String route;
}
