package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    
    public boolean createCommonUser(AccountDto accountDto){
        Account account = new Account();
        account.setUsername(accountDto.getUsername());

        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());     // 암호화 해야함
        account.setPassword(encodedPassword);

        account.setTimeInform(new TimeInform());
        account.setRole(roleService.findRole("ROLE_USER"));

        try {
            accountRepository.save(account);
            return true;

        } catch (Exception e){
            return false;
        }

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
    
    public AccountInfoResponse findAccountInfoByUsername(String username){
        Account account = getAccount(username);

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse(
            account.getUsername(),
            account.getRole().getRoleName(),
            account.getTimeInform()
        );

        return accountInfoResponse;
    }

    public void updatePassword(String username, String rawPassword){
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Account account = getAccount(username);
        account.setPassword(encodedPassword);
        account.getTimeInform().renewUpdatedTime();

        accountRepository.save(account);
    }

    public void updateAccountRole(String username, String roleName){
        Account account = getAccount(username);
        account.setRole(roleService.findRole(roleName));

        accountRepository.save(account);
    }

    public boolean isMatchedPassword(String username, String rawPassword){
        Account account = getAccount(username);

        return passwordEncoder.matches(rawPassword, account.getPassword());
    }

    private Account getAccount(String username) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isEmpty()){
            log.info("유저 없음");
        }
        Account account = optionalAccount.get();
        return account;
    }

    public Page<AccountInfoResponse> search(SearchDto searchDto, int page, int size){
        try {
            Pageable pageable = PageRequest.of(page - 1, size);

            return accountRepository.search(searchDto, pageable);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public void deleteAccount(String username){
        Account account = getAccount(username);
        accountRepository.delete(account);
    }
}
