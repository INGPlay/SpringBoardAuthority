package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.repository.custom.RoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {

    public Optional<Role> findByRoleName(String roleName);

    public List<String> getRoleNames();
}
