package crud.board.BoardAuthority.repository.custom;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.entity.thread.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    public Page<Post> search(SearchDto searchDto, Pageable pageable);
}
