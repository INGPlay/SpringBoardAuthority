package crud.board.BoardAuthority.security.lib;

import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.security.authenticationManager.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationSupport {

    private final CustomUserDetailsService customUserDetailsService;
    private final SessionRegistry sessionRegistry;

    public void updateSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(
                updateAuthentcation(authentication, accountContext.getUsername())
        );
    }

    private Authentication updateAuthentcation(Authentication auth, String username){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, auth.getCredentials(), userDetails.getAuthorities()
        );
        newAuth.setDetails(auth.getDetails());
        return newAuth;
    }

    public void forceLogoutUser(String username){

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (Object principal : allPrincipals){
            AccountContext accountContext = (AccountContext) principal;

            if (accountContext.getUsername().equals(username)){
                
                // 사용자의 모든 세션을 가져옴
                List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, false);
                allSessions.forEach(s -> s.expireNow());

                break;
            }
        }
    }
}
