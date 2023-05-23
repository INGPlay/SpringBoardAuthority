package crud.board.BoardAuthority;

import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.repository.CommentRepository;
import crud.board.BoardAuthority.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class BoardAuthorityApplicationTests {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	void contextLoads() {
		commentRepository.getCommentsByPostId(1L);
		commentRepository.getLastGroupIndex(1L);
	}

}
