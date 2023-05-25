package crud.board.BoardAuthority.controller.thread;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.dto.ThreadDto;
import crud.board.BoardAuthority.domain.dto.UpdatePostDto;
import crud.board.BoardAuthority.domain.form.post.CreatePostForm;
import crud.board.BoardAuthority.domain.form.post.UpdatePostForm;
import crud.board.BoardAuthority.domain.response.comment.CommentResponse;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.CommentService;
import crud.board.BoardAuthority.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    // Read
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable Long postId,
                           Model model){
        // 게시글
        PostDto post = postService.findPostById(postId);
        model.addAttribute("post", post);

        // 댓글
        List<CommentResponse> commentResponses = commentService.getCommentsByPostId(postId);
        model.addAttribute("commentResponses", commentResponses);
        log.info("{}", commentResponses.stream().map(c -> c.getContent()).collect(Collectors.toList()));

        return "thread/post";
    }

    // Create
    @GetMapping("/create-post")
    public String createPostForm(Model model){

        CreatePostForm createPostForm = new CreatePostForm("", "");

        model.addAttribute("createPostForm", createPostForm);

        return "thread/createPostForm";
    }

    @PostMapping("/create-post")
    public String createPost(@Valid CreatePostForm createPostForm,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal AccountContext accountContext){

        if (accountContext == null){
            log.info("권한 없음");
            return "redirect:/thread";
        }
        String username = accountContext.getAccountDto().getUsername();

        log.info("{}", bindingResult);
        if (bindingResult.hasErrors()){

            return "thread/createPostForm";
        }

        ThreadDto threadDto = new ThreadDto();
        threadDto.setUsername(username);
        threadDto.setTitle(createPostForm.getTitle());
        threadDto.setContent(createPostForm.getContent());

        postService.createPost(threadDto);

        return "redirect:/thread";
    }

    // Update
    @GetMapping("/update-post/{postId}")
    public String updatePostForm(@PathVariable Long postId,
                                 Model model,
                                 @AuthenticationPrincipal AccountContext context){

        PostDto post = postService.findPostById(postId);

        // 포스트의 작성자와 로그인 사용자가 같지 않을 경우 에러
        if (!post.getUsername().equals(context.getUsername())){
            throw new RuntimeException();
        }

        UpdatePostForm updatePostForm = new UpdatePostForm(post.getPostId(), post.getTitle(), post.getContent());

        model.addAttribute("updatePostForm", updatePostForm);

        return "thread/updatePostForm";
    }

    @PostMapping("/update-post")
    public String updatePost(@Valid UpdatePostForm updatePostForm,
                             BindingResult bindingResult,
                             Model model){
        log.info("{}", bindingResult);

        // Validation
        if (bindingResult.hasErrors()){
            model.addAttribute("updatePostForm", updatePostForm);
            return "thread/updatePostForm";
        }

        UpdatePostDto updatePostDto = new UpdatePostDto(
                updatePostForm.getPostId(),
                updatePostForm.getTitle(),
                updatePostForm.getContent()
        );
        postService.updatePost(updatePostDto);

        return "redirect:/thread/post/" + updatePostForm.getPostId();
    }

    // Delete
    @PostMapping("/delete-post")
    public String deletePost(@RequestParam Long postId,
                             @AuthenticationPrincipal AccountContext context){
        log.info("[postId] {}",postId);

        PostDto post = postService.findPostById(postId);
        // 포스트의 작성자와 로그인 사용자가 같지 않을 경우 에러
        if (!post.getUsername().equals(context.getUsername())){
            throw new RuntimeException();
        }

        postService.deletePost(postId);

        return "redirect:/thread";
    }
}
