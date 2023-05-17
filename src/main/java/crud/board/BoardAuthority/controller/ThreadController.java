package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.response.PagingPostResponse;
import crud.board.BoardAuthority.domain.response.ThreadResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingInform;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.service.PostService;
import crud.board.BoardAuthority.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadController {

    private final ThreadService threadService;
    private final PostService postService;

    @GetMapping
    public String thread(Model model){

        model.addAttribute(model.addAttribute("threadResponses", getThreadResponse(model)));

        return "thread/thread";
    }

    @GetMapping("/{threadId}")
    public String threadSelect(@PathVariable Long threadId,
                               @RequestParam(defaultValue = "1") int postPage,
                               @RequestParam(defaultValue = "10") int postSize,
                               Model model,
                               RedirectAttributes redirectAttributes){

        // 페이지가 1보다 작은 경우
        if (postPage < 1){
            redirectAttributes.addAttribute("postPage", 1);
            redirectAttributes.addAttribute("postSize", postSize);
            return "redirect:/thread/" + threadId;
        }

        Page<Post> pagingPost = postService.pagePost(threadId, postPage - 1, postSize);

        // 페이지가 최대 범위를 넘어선 경우
        if (postPage > pagingPost.getTotalPages()){
            redirectAttributes.addAttribute("postPage", pagingPost.getTotalPages());
            redirectAttributes.addAttribute("postSize", postSize);
            return "redirect:/thread/" + threadId;
        }

        model.addAttribute(model.addAttribute("threadResponses", getThreadResponse(model)));
        model.addAttribute("pagingPost", pagingPost);

        PagingRange<Post> pagingRange = new PagingRange<>(pagingPost, 10);

        model.addAttribute("pagingRange", pagingRange);

        return "thread/thread";
    }

    private List<ThreadResponse> getThreadResponse(Model model){
        return threadService.pageThreadResponseByCreatedTime(0, 5).toList();
    }

    @GetMapping("/{threadId}/{postId}")
    public String viewPost(@PathVariable Long threadId,
                           @PathVariable Long postId){

        return "thread/post";
    }
}
