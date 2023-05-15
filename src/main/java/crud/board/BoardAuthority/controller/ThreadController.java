package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.entity.thread.Thread;
import crud.board.BoardAuthority.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping
    public String thread(){

        return "thread/thread";
    }

    @GetMapping("/{threadIndex}")
    public String threadSelect(@PathVariable Long threadIndex,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size){

        Page<Thread> threads = threadService.pageThreadByCreatedTime(page, size);
        Page<String> map = threads.map(t -> t.getTitle());

        return "thread/thread";
    }
}
