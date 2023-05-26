package crud.board.BoardAuthority.repository.custom;

import java.util.List;
import java.util.Set;

public interface RoleRepositoryCustom {
    public List<String> getRoleNames();

    public List<String> getRoutes(String roleName);
}
