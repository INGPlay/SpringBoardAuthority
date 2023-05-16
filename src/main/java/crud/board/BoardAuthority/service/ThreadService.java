package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.domain.response.ThreadResponse;
import crud.board.BoardAuthority.entity.authentication.Path;
import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.entity.thread.Thread;
import crud.board.BoardAuthority.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
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
@Transactional
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final PathService pathService;

    public void createThread(String title, String route){
        Thread thread = new Thread();
        thread.setTitle(title);
        thread.setTimeInform(new TimeInform());
        thread.setPath(pathService.makePathObject(route));

        threadRepository.save(thread);
    }

    public Thread findThread(String title) {
        return threadRepository.findByTitle(title).orElse(null);
    }

    public Page<Thread> pageThreadByCreatedTime(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeInform.createdTime"));
        return threadRepository.findAll(pageable);
    }

    public Page<ThreadResponse> pageThreadResponseByCreatedTime(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeInform.createdTime"));

        return threadRepository.findAll(pageable).map(t ->
                new ThreadResponse(t.getTitle(), t.getPath().getRoute())
        );
    }
}
