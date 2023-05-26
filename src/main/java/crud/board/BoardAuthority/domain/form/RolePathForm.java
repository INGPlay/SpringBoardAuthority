package crud.board.BoardAuthority.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class RolePathForm {
    @NotNull
    private String roleName;

    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9-*/, ]*)$")
    private String formattedRoutes;
}
