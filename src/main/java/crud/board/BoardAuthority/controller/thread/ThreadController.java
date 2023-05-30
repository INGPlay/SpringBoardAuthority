package crud.board.BoardAuthority.controller.thread;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/thread")
public class ThreadController {
    private final PostService postService;

    // 게시판 페이징
    @GetMapping
    public String viewThread(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String searchOption,
                             @RequestParam(required = false) String searchWord,
                             Model model,
                             RedirectAttributes redirectAttributes){

/*        log.info("[searchWord] {}", searchWord);
        log.info("[searchOption] {}", searchOption);*/
        SearchDto searchDto = new SearchDto(searchWord, searchOption);
        Page<Post> pagingPost = postService.searchPost(
                searchDto,
                page, size
        );

        if (pagingPost == null){
            return "thread/thread";
        }

        if (StringUtils.hasText(searchOption) && StringUtils.hasText(searchWord)){
            model.addAttribute("searchOption", searchDto.getSearchOption());
            model.addAttribute("searchWord", searchDto.getSearchWord());
        }

        model.addAttribute("pagingPost", pagingPost);

        PagingRange<Post> pagingRange = new PagingRange<>(pagingPost, 10);

        model.addAttribute("pagingRange", pagingRange);

        return "thread/thread";
    }

    private boolean checkPageRange(int postPage, int postSize, RedirectAttributes redirectAttributes, Page<Post> pagingPost) {
        // 페이지가 1보다 작은 경우
        if (postPage < 1){
            redirectAttributes.addAttribute("page", 1);
            redirectAttributes.addAttribute("size", postSize);
            return false;

            // 페이지가 최대 범위를 넘어선 경우
        } else if (postPage > pagingPost.getTotalPages()){
            redirectAttributes.addAttribute("page", pagingPost.getTotalPages());
            redirectAttributes.addAttribute("size", postSize);
            return false;
        }

        return true;
    }


}
