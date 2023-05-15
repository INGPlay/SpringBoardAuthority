package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.entity.thread.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
//    public Optional<Thread> findByTitle(String Title);
}
