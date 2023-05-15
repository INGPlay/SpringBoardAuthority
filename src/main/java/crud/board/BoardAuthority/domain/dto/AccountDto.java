package crud.board.BoardAuthority.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountDto {
    private String username;
    private String password;
}
