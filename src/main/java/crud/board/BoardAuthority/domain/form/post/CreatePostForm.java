package crud.board.BoardAuthority.domain.form.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class CreatePostForm {

    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    @Size(max = 1000)
    private String content;
}
