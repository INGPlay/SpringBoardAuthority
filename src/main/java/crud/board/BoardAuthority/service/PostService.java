package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.dto.PostDto;
import crud.board.BoardAuthority.domain.dto.ThreadDto;
import crud.board.BoardAuthority.domain.dto.UpdatePostDto;
import crud.board.BoardAuthority.entity.account.Account;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.repository.AccountRepository;
import crud.board.BoardAuthority.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    public Page<Post> pagePost(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeInform.createdTime"));

        return postRepository.findAll(pageable);
    }

    public PostDto findPostById(Long postId){
        Post post = getPost(postId);

        PostDto postDto = new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAccount().getUsername(),
                post.getTimeInform()
        );

        return postDto;
    }

    public void createPost(ThreadDto threadDto){
        Optional<Account> optionalAccount = accountRepository.findByUsername(threadDto.getUsername());
        if (optionalAccount.isEmpty()){
            log.info("계정 없음 에러");
        }

        Account account = optionalAccount.get();

        Post post = new Post();
        post.setAccount(account);
        post.setTitle(threadDto.getTitle());
        post.setContent(threadDto.getContent());
        post.setTimeInform(new TimeInform());

        postRepository.save(post);
    }

    public void updatePost(UpdatePostDto updatePostDto){
        Post post = getPost(updatePostDto.getPostId());

        post.setTitle(updatePostDto.getTitle());
        post.setContent(updatePostDto.getContent());
        post.getTimeInform().renewUpdatedTime();

        postRepository.save(post);
    }

    private Post getPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            log.info("id에 해당하는 post 없음");
        }
        return optionalPost.get();
    }
}
