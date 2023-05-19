package crud.board.BoardAuthority.entity.account.embeddables;

import crud.board.BoardAuthority.entity.authentication.Role;
import crud.board.BoardAuthority.entity.thread.Comment;
import crud.board.BoardAuthority.entity.thread.CommentPlus;
import crud.board.BoardAuthority.entity.thread.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter
public class AccountInform {

    // Post
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    // Comment
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
