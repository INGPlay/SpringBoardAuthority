package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.repository.custom.AccountRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {
    public Optional<Account> findByUsername(String username);

    public Page<AccountInfoResponse> search(SearchDto searchDto, Pageable pageable);
}
