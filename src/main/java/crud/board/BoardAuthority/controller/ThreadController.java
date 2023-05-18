package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.dto.UpdatePostDto;
import crud.board.BoardAuthority.domain.form.post.CreatePostForm;
import crud.board.BoardAuthority.domain.form.post.UpdatePostForm;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal User user){

        Page<Post> pagingPost = postService.pagePost(postPage - 1, postSize);

        boolean x = checkPageRange(postPage, postSize, redirectAttributes, pagingPost);
        if (!x) {
            return "thread/thread";
        }

        if (user != null){
            model.addAttribute("loginUsername", user.getUsername());
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

    // Read
    @GetMapping("/post/{postId}")
    public String viewPost(@PathVariable Long postId,
                           Model model){

        PostDto post = postService.findPostById(postId);

        model.addAttribute("post", post);

        return "thread/post";
    }

    // Create
    @GetMapping("/create-post")
    public String createPostForm(Model model){

        CreatePostForm createPostForm = new CreatePostForm("", "");

        model.addAttribute("createPostForm", createPostForm);

        return "thread/createPostForm";
    }

    // Update
    @GetMapping("/update-post/{postId}")
    public String updatePostForm(@PathVariable Long postId,
                                 Model model){

        PostDto post = postService.findPostById(postId);

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
}
