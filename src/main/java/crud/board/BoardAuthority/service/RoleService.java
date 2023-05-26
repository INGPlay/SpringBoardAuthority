package crud.board.BoardAuthority.service;

import antlr.actions.python.CodeLexer;
import crud.board.BoardAuthority.domain.response.RolePathResponse;
import crud.board.BoardAuthority.entity.authentication.Path;
import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.repository.PathRepository;
import crud.board.BoardAuthority.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PathService pathService;

    public void createRole(String roleName){
        Role role = new Role(roleName);

        roleRepository.save(role);
    }

    public void addPath(String roleName, String route){
        Role role = findRole(roleName);

        Path path = pathService.findOrCreatePath(route);

        role.addPath(path);

        roleRepository.save(role);
    }

    public void setPaths(String roleName, Collection<String> routes){

        Role role = findRole(roleName);

        Set<Path> newPaths = routes.stream().map(r ->
                pathService.findOrCreatePath(r.strip())
        ).collect(Collectors.toSet());

        role.setPaths(newPaths);
    }

    public void addPaths(String roleName, Collection<String> routes){
        Role role = findRole(roleName);

        for (String route : routes){
            role.addPath(pathService.findOrCreatePath(route));
            roleRepository.save(role);
        }
    }

    public Role findRole(String roleName){
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    public List<String> getRoleNames(){

        List<String> roleNameSet = roleRepository.getRoleNames();

        return roleNameSet;
    }

    public List<String> getRoleNamesNotInAdmin(){
        List<String> roleNames = getRoleNames();
        roleNames.remove("ROLE_ADMIN");

        return roleNames;
    }

    public List<String> getRoutes(String roleName){
        List<String> routes = roleRepository.getRoutes(roleName);

        return routes;
    }
}
