package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.dto.comment.CommentDto;
import crud.board.BoardAuthority.domain.response.comment.CommentResponse;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.thread.Comment;
import crud.board.BoardAuthority.entity.thread.CommentPlus;
import crud.board.BoardAuthority.repository.AccountRepository;
import crud.board.BoardAuthority.repository.CommentRepository;
import crud.board.BoardAuthority.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponse> getCommentsByPostId(Long postId){
        return commentRepository.getCommentsByPostId(postId);
    }

    // 그냥 댓글
    public void createNewComment(CommentDto commentDto){
        CommentPlus commentPlus = new CommentPlus();
        commentPlus.setCommentGroup(
                commentRepository.getLastGroupIndex(commentDto.getPostId()).orElse(0L) + 1
        );
        commentPlus.setCommentDepth(0L);

        Comment comment = new Comment();
        comment.setPost(postRepository.findById(commentDto.getPostId()).get());
        comment.setAccount(accountRepository.findByUsername(commentDto.getUsername()).get());
        comment.setContent(commentDto.getContent());
        comment.setTimeInform(new TimeInform());
        comment.setCommentPlus(commentPlus);

        commentRepository.save(comment);
    }

    // 대댓글
    public void createNewCommentToComment(CommentDto commentDto, Long commentGroup){
        CommentPlus commentPlus = new CommentPlus();
        commentPlus.setCommentGroup(commentGroup);
        commentPlus.setCommentDepth(1L);

        Comment comment = new Comment();
        comment.setPost(postRepository.findById(commentDto.getPostId()).get());
        comment.setAccount(accountRepository.findByUsername(commentDto.getUsername()).get());
        comment.setContent(commentDto.getContent());
        comment.setTimeInform(new TimeInform());
        comment.setCommentPlus(commentPlus);

        commentRepository.save(comment);
    }
}
