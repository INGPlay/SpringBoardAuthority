package crud.board.BoardAuthority.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class RolePathResponse {
    private String roleName;
    private Set<String> routes;
}
