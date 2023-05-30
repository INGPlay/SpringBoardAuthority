package crud.board.BoardAuthority.domain.form.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class RoleForm {
    @Size(min = 4, max = 10)
    private String roleName;
}
