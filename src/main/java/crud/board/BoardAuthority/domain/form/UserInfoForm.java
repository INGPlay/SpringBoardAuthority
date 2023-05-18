package crud.board.BoardAuthority.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserInfoForm {

    public UserInfoForm(String password, String passwordCheck) {
        this.password = password;
        this.passwordCheck = passwordCheck;
    }

    /**'
     * @Range를 Pattern에서 정규표현식으로 빼고 길이 제한을 할 수도 있지만
     * 길이 에러 메시지와 패턴 메시지를 따로 주기 위해 다 붙임
     */
    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String password;

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String passwordCheck;
}
