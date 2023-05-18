package crud.board.BoardAuthority.domain.response;

import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountInfoResponse {

    private String username;
    private String role;
    private TimeInform timeInform;

    public AccountInfoResponse(String username, String role, TimeInform timeInform) {
        this.username = username;
        this.timeInform = timeInform;
        this.role = role;
    }
}
