package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.response.PagingPostResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PostResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingInform;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.entity.thread.Thread;
import crud.board.BoardAuthority.repository.AccountRepository;
import crud.board.BoardAuthority.repository.PostRepository;
import crud.board.BoardAuthority.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final ThreadRepository threadRepository;
    private final PathService pathService;

    public PagingPostResponse pagePost(Long threadId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeInform.createdTime"));

        Page<Post> pagePost = postRepository.findPostByThreadId(threadId, pageable);

        Optional<Thread> optionalThread = threadRepository.findById(threadId);
        if (optionalThread.isEmpty()){
            log.info("스레드 비었다");
        }

        int startNum = pagePost.getPageable().getPageNumber() + 1;
        int endNum = pagePost.getPageable().getPageNumber() + pagePost.getNumberOfElements();

        PagingInform pagingInform = new PagingInform(
                startNum, endNum, pagePost.hasPrevious(), pagePost.hasNext()
        );
        

        List<PostResponse> postResponseList = pagePost.map(p -> new PostResponse(
                        p.getAccount().getUsername(),
                        p.getTitle(), p.getContent(),
                        p.getTimeInform().getCreatedTime(),
                        p.getTimeInform().getUpdatedTime(),
                        p.getPath().getRoute()
                )
        ).toList();

        return new PagingPostResponse(pagingInform, postResponseList);
    }

    public void createPost(PostDto postDto){
        Optional<Account> optionalAccount = accountRepository.findByUsername(postDto.getUsername());
        if (optionalAccount.isEmpty()){
            log.info("계정 없음 에러");
        }

        Optional<Thread> optionalThread = threadRepository.findById(postDto.getThreadId());
        if (optionalThread.isEmpty()){
            log.info("스레드 없음 에러");
        }

        Account account = optionalAccount.get();
        Thread thread = optionalThread.get();
        Long threadPostId = updateThreadSequence(thread);

        Post post = new Post();
        post.setAccount(account);
        post.setThreadPostId(threadPostId);
        post.setThread(thread);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTimeInform(new TimeInform());
        post.setPath(pathService.makePathObject(thread.getPath().getRoute() + "/" + threadPostId));

        postRepository.save(post);
    }

    private Long updateThreadSequence(Thread thread){
        thread.updatePostSequence();
        return thread.getPostSeqence();
    }
}
