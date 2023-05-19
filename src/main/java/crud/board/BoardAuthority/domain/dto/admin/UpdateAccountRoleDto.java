package crud.board.BoardAuthority.domain.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateAccountRoleDto {
    private String username;
    private String roleName;
}
