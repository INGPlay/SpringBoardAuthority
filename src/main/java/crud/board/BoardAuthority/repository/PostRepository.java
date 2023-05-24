package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.repository.custom.PostRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    public Page<Post> search(SearchDto searchDto, Pageable pageable);
}
