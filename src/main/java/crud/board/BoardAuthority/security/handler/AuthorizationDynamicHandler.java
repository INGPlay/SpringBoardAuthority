package crud.board.BoardAuthority.security.handler;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.repository.RoleRepository;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationDynamicHandler {

    private final RoleRepository roleRepository;

    public boolean isAuthorization(HttpServletRequest request, Authentication authentication){
        Object principal = authentication.getPrincipal();
        log.info("{}", (principal instanceof AccountContext));
        
        // 권한이 없는 경우
        if (!(principal instanceof AccountContext)){
            return false;
        }
        AccountContext accountContext = (AccountContext) principal;

        Collection<GrantedAuthority> authorities = accountContext.getAuthorities();

        String requestURI = request.getRequestURI();

        for (GrantedAuthority authority : authorities) {

            List<String> routes = getRoutes(authority);

            if (routes.contains(requestURI)){
                return true;
            }

        }

        // true 면 통과
        return false;
    }

    private List<String> getRoutes(GrantedAuthority authority) {
        Role roleName = getRoleName(authority.getAuthority());
        List<String> routes = roleName.getPaths().stream().map(p -> p.getRoute()).collect(Collectors.toList());
        return routes;
    }

    private Role getRoleName(String roleName){
        Optional<Role> optionalRole = roleRepository.findByRoleName(roleName);
        if (optionalRole.isEmpty()){
            log.info("role이 빔");
        }
        return optionalRole.get();
    }
}
