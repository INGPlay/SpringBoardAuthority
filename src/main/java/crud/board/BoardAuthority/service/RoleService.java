package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public void createRole(String roleName){
        Role role = new Role(roleName);

        roleRepository.save(role);
    }

    public Role findRole(String roleName){
        return roleRepository.findByRoleName(roleName).orElse(null);
    }
}
