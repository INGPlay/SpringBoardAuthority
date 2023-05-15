package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.entity.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByRoleName(String roleName);
}
