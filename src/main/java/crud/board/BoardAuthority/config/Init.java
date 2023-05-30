package crud.board.BoardAuthority.config;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.domain.dto.thread.ThreadDto;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.PostService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

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
        initRole();
        initUser();
        initPost();
    }

    @Transactional
    private void initRole(){

        final String ROLE_USER = "ROLE_USER";
        final String ROLE_MANAGER = "ROLE_MANAGER";
        final String ROLE_ADMIN = "ROLE_ADMIN";

        roleService.createRole(ROLE_USER);
        roleService.createRole(ROLE_MANAGER);
        roleService.createRole(ROLE_ADMIN);

        roleService.addPath(ROLE_USER, "/thread/post/**");
        roleService.addPath(ROLE_USER, "/thread/comment/**");
        roleService.addPath(ROLE_USER, "/account");

        roleService.addPath(ROLE_MANAGER, "/admin/account/**");
    }

    private void initUser(){
        accountService.createCommonUser(new AccountDto("user", "1111"));
        accountService.createUser(new AccountDto("manager", "1111"), "ROLE_MANAGER");
        accountService.createUser(new AccountDto("admin", "1111"), "ROLE_ADMIN");

        for (int i = 1; i <= 125; i++){
            accountService.createCommonUser(new AccountDto("user"+ i, "1111"));
        }
    }

    private void initPost() {
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
