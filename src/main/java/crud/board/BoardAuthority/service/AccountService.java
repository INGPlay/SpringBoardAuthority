package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void createCommonUser(AccountDto accountDto){
        Account account = new Account();
        account.setUsername(accountDto.getUsername());

        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());     // 암호화 해야함
        account.setPassword(encodedPassword);

        account.setTimeInform(new TimeInform());
        account.setRole(roleService.findRole("ROLE_USER"));

        accountRepository.save(account);
    }

    public void createUser(AccountDto accountDto, String roleName){
        Account account = new Account();
        account.setUsername(accountDto.getUsername());

        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());     // 암호화 해야함
        account.setPassword(encodedPassword);

        account.setTimeInform(new TimeInform());
        account.setRole(roleService.findRole(roleName));

        accountRepository.save(account);
    }
}
