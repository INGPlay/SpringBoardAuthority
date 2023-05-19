package crud.board.BoardAuthority.security.authenticationManager;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    private final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    private final GrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority("ROLE_MANAGER");
    private final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("UsernameNotFoundException")
                );

        List<GrantedAuthority> roles = handleRole(account.getRole().getRoleName());

        return new AccountContext(new AccountDto(account.getUsername(), account.getPassword()), roles);
    }

    private List<GrantedAuthority> handleRole(String roleName){
        List<GrantedAuthority> roles = new ArrayList<>();

        String[] tokens = roleName.split("_");

        if (tokens[1].equals("USER")){

        } else if (tokens[1].equals("MANAGER")){
            roles.add(ROLE_USER);

        } else if (tokens[1].equals("ADMIN")) {
            roles.add(ROLE_USER);
            roles.add(ROLE_MANAGER);
        } else {
            log.info("--------------잘못된 권한-------------");
        }
        roles.add(new SimpleGrantedAuthority(roleName));

        return roles;
    }
}
