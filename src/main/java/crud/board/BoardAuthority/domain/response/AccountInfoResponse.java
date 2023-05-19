package crud.board.BoardAuthority.domain.response;

import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import lombok.Getter;

@Getter
public class AccountInfoResponse {

    private String username;
    private String roleName;
    private TimeInform timeInform;

    public AccountInfoResponse(String username, String roleName, TimeInform timeInform) {
        this.username = username;
        this.timeInform = timeInform;
        this.roleName = roleName;
    }
}
