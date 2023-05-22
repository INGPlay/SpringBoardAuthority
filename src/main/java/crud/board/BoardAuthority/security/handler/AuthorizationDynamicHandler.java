package crud.board.BoardAuthority.security.handler;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.repository.RoleRepository;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.PathService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationDynamicHandler {

    private final RoleService roleService;

    public boolean isAuthorization(HttpServletRequest request, Authentication authentication){
        Object principal = authentication.getPrincipal();

        // 권한이 없는 경우
        if (!(principal instanceof AccountContext)){
            return false;
        }
        
        // 권한이 있다면
        AccountContext accountContext = (AccountContext) principal;

        Collection<GrantedAuthority> authorities = accountContext.getAuthorities();

        String requestRoute = request.getRequestURI();

        for (GrantedAuthority authority : authorities) {
            String auth = authority.getAuthority();

            // ADMIN 이면 무조건 통과
            if (auth.equals("ROLE_ADMIN")){
                return true;
            }
            
            Set<String> routes = roleService.getRoutes(auth);

            for (String pattern : routes){
                if (antPathMatcher().match(pattern, requestRoute)){

                    // true 면 통과
                    return true;
                }
            }

        }

        return false;
    }

    @Bean
    private AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }
}
