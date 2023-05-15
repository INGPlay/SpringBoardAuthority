package crud.board.BoardAuthority.security.authenticationManager;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.entity.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountContext extends User {
    private final AccountDto accountDto;

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public AccountContext(AccountDto accountDto, Collection<? extends GrantedAuthority> authorities) {
        super(accountDto.getUsername(), accountDto.getPassword(), authorities);
        this.accountDto = accountDto;
    }
}
