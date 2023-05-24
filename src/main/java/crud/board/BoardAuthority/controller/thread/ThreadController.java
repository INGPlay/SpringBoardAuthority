package crud.board.BoardAuthority.controller.thread;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.dto.ThreadDto;
import crud.board.BoardAuthority.domain.dto.UpdatePostDto;
import crud.board.BoardAuthority.domain.dto.comment.CommentDto;
import crud.board.BoardAuthority.domain.form.post.CreatePostForm;
import crud.board.BoardAuthority.domain.form.post.UpdatePostForm;
import crud.board.BoardAuthority.domain.response.comment.CommentResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.CommentService;
import crud.board.BoardAuthority.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadController {
    private final PostService postService;

    // 게시판 페이징
    @GetMapping
    public String viewThread(@RequestParam(defaultValue = "1") int postPage,
                             @RequestParam(defaultValue = "10") int postSize,
                             @RequestParam(required = false) String target,
                             @RequestParam(required = false) String search,
                             Model model,
                             RedirectAttributes redirectAttributes){

        Page<Post> pagingPost = postService.pagePost(postPage, postSize);
        if (pagingPost == null){
            return "thread/thread";
        }

        boolean isInRange = checkPageRange(postPage, postSize, redirectAttributes, pagingPost);
        if (!isInRange) {
            return "redirect:/thread";
        }

        model.addAttribute("pagingPost", pagingPost);

        PagingRange<Post> pagingRange = new PagingRange<>(pagingPost, 10);

        model.addAttribute("pagingRange", pagingRange);

        return "thread/thread";
    }

    private boolean checkPageRange(int postPage, int postSize, RedirectAttributes redirectAttributes, Page<Post> pagingPost) {
        // 페이지가 1보다 작은 경우
        if (postPage < 1){
            redirectAttributes.addAttribute("postPage", 1);
            redirectAttributes.addAttribute("postSize", postSize);
            return false;

            // 페이지가 최대 범위를 넘어선 경우
        } else if (postPage > pagingPost.getTotalPages()){
            redirectAttributes.addAttribute("postPage", pagingPost.getTotalPages());
            redirectAttributes.addAttribute("postSize", postSize);
            return false;
        }

        return true;
    }


}
