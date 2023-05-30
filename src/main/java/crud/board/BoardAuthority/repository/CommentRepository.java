package crud.board.BoardAuthority.repository;

import crud.board.BoardAuthority.domain.response.comment.CommentResponse;
import crud.board.BoardAuthority.entity.thread.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select new crud.board.BoardAuthority.domain.response.comment.CommentResponse" +
            "(" +
            "c.post.id, c.account.username, c.content, c.timeInform.createdTime, c.timeInform.updatedTime, c.commentPlus.commentGroup, c.commentPlus.commentDepth" +
            ")" +
            " from Comment c" +
            " where c.post.id = :postId" +
            " order by c.commentPlus.commentGroup asc, c.commentPlus.commentDepth asc, c.timeInform.createdTime asc")
    public List<CommentResponse> getCommentsByPostId(Long postId);

    @Query("select max(c.commentPlus.commentGroup) from Comment c where c.post.id = :postId")
    public Optional<Long> getLastGroupIndex(Long postId);
}
