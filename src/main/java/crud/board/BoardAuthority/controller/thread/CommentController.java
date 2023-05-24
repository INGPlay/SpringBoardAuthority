package crud.board.BoardAuthority.controller.thread;

import crud.board.BoardAuthority.domain.dto.comment.CommentDto;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread/comment")
public class CommentController {
    private final CommentService commentService;

    // Comment 생성
    @PostMapping("/create-comment/{postId}")
    public String createComment(@PathVariable Long postId,
                                @RequestParam String content,
                                @AuthenticationPrincipal AccountContext accountContext){

        CommentDto commentDto = new CommentDto(
                postId, accountContext.getAccountDto().getUsername(), content
        );
        commentService.createNewComment(commentDto);

        return "redirect:/thread/post/" + postId;
    }

    @PostMapping("/create-comment/{postId}/{commentGroup}")
    public String createCommentToComment(@PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "commentGroup") Long commentGroup,
                                         @RequestParam String content,
                                         @AuthenticationPrincipal AccountContext accountContext){

        CommentDto commentDto = new CommentDto(
                postId, accountContext.getAccountDto().getUsername(), content
        );
        commentService.createNewCommentToComment(commentDto, commentGroup);

        return "redirect:/thread/post/" + commentDto.getPostId();
    }
}
