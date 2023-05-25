package crud.board.BoardAuthority.repository.custom;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.entity.thread.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRepositoryCustom {
    public Page<AccountInfoResponse> search(SearchDto searchDto, Pageable pageable);
}
