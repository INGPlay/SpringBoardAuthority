package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.response.PagingPostResponse;
import crud.board.BoardAuthority.domain.response.ThreadResponse;
import crud.board.BoardAuthority.entity.thread.Thread;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadController {

    private final ThreadService threadService;
    private final PostService postService;

    @GetMapping
    public String thread(Model model){

        addThreadResponse(model);

        return "thread/thread";
    }

    @GetMapping("/{threadId}")
    public String threadSelect(@PathVariable Long threadId,
                               @RequestParam(defaultValue = "0") int postPage,
                               @RequestParam(defaultValue = "10") int postSize,
                               Model model){

        addThreadResponse(model);

        PagingPostResponse pagingPostResponse = postService.pagePost(threadId, postPage, postSize);

        model.addAttribute("postResponses", pagingPostResponse.getPostResponses());
        model.addAttribute("pagingInform", pagingPostResponse.getPagingInform());

        return "thread/thread";
    }

    private void addThreadResponse(Model model){
        List<ThreadResponse> threadResponses = threadService.pageThreadResponseByCreatedTime(0, 5).toList();

        model.addAttribute(model.addAttribute("threadResponses", threadResponses));
    }
}
