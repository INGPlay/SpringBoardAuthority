package crud.board.BoardAuthority.controller.thread;

import crud.board.BoardAuthority.domain.dto.comment.CommentDto;
import crud.board.BoardAuthority.domain.response.comment.CommentResponse;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                @AuthenticationPrincipal AccountContext accountContext,
                                Model model){

        CommentDto commentDto = new CommentDto(
                postId, accountContext.getAccountDto().getUsername(), content
        );
        commentService.createNewComment(commentDto);

//        return "redirect:/thread/post/" + postId;
        return listComments(model, postId);
    }

    // 대댓글 -> Ajax로 다시 만들기
    @PostMapping("/create-comment/{postId}/{commentGroup}")
    public String createCommentToComment(@PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "commentGroup") Long commentGroup,
                                         @RequestParam String content,
                                         @AuthenticationPrincipal AccountContext accountContext,
                                         Model model){

        CommentDto commentDto = new CommentDto(
                postId, accountContext.getAccountDto().getUsername(), content
        );
        commentService.createNewCommentToComment(commentDto, commentGroup);

//        return "redirect:/thread/post/" + commentDto.getPostId();
        return listComments(model, postId);
    }

    @GetMapping("/{postId}")
    public String getComments(@PathVariable(name = "postId") Long postId,
                              Model model){

        return listComments(model, postId);
    }

    private String listComments(Model model, Long postId){
        List<CommentResponse> commentResponses = commentService.getCommentsByPostId(postId);
        model.addAttribute("commentResponses", commentResponses);

        return "thread/post :: #commentList";
    }
}
