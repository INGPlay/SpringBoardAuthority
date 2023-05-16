package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.entity.authentication.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
}
