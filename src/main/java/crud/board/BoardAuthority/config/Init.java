package crud.board.BoardAuthority.config;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.domain.dto.ThreadDto;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.PostService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class Init implements InitializingBean{

    private final RoleService roleService;
    private final AccountService accountService;
    private final PostService postService;

    @Override
    public void afterPropertiesSet() {
        init(); //메소드 최초 호출시키기
    }

    public void init() {
        initUser();
        initPost();
    }

    public void initUser(){
        roleService.createRole("ROLE_USER");
        roleService.createRole("ROLE_MANAGER");
        roleService.createRole("ROLE_ADMIN");

        accountService.createCommonUser(new AccountDto("user", "1111"));
        accountService.createUser(new AccountDto("manager", "1111"), "ROLE_MANAGER");
        accountService.createUser(new AccountDto("admin", "1111"), "ROLE_ADMIN");
    }

    public void initPost() {
        for (int i = 1; i <= 125; i++){
            ThreadDto threadDto = new ThreadDto();
            threadDto.setUsername("user");
            threadDto.setTitle(i + "번째 글입니다");
            threadDto.setContent("내용" + i);

            postService.createPost(threadDto);
        }

        for (int i = 1; i <= 125; i++){
            ThreadDto threadDto = new ThreadDto();
            threadDto.setUsername("manager");
            threadDto.setTitle(i + "번째 글입니다");
            threadDto.setContent("내용" + i);

            postService.createPost(threadDto);
        }
        for (int i = 1; i <= 125; i++){
            ThreadDto threadDto = new ThreadDto();
            threadDto.setUsername("admin");
            threadDto.setTitle(i + "번째 글입니다");
            threadDto.setContent("내용" + i);

            postService.createPost(threadDto);
        }

    }
}
