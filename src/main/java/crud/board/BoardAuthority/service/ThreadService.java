package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.entity.general.embeddables.TimeInform;
import crud.board.BoardAuthority.entity.thread.Thread;
import crud.board.BoardAuthority.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final RoleService roleService;

    public void createThread(String title){
        Thread thread = new Thread();
        thread.setTitle(title);
        thread.setRole(roleService.findRole("ROLE_USER"));
        thread.setTimeInform(new TimeInform());

        threadRepository.save(thread);
    }

    public Page<Thread> pageThreadByCreatedTime(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeInform.createdTime"));
        return threadRepository.findAll(pageable);
    }
}
