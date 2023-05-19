package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.dto.ThreadDto;
import crud.board.BoardAuthority.domain.dto.UpdatePostDto;
import crud.board.BoardAuthority.domain.form.post.CreatePostForm;
import crud.board.BoardAuthority.domain.form.post.UpdatePostForm;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
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
    @PostMapping("/delete-post")
    public String deletePost(@RequestParam Long postId){
        log.info("[postId] {}",postId);
        postService.deletePost(postId);

        return "redirect:/thread";
    }
}
