package crud.board.BoardAuthority.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Path {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long id;

    // Role
}
