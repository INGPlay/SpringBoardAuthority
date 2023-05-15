package crud.board.BoardAuthority.config;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.entity.thread.Thread;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.RoleService;
import crud.board.BoardAuthority.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Init implements InitializingBean{

    private final RoleService roleService;
    private final AccountService accountService;
    private final ThreadService threadService;

    @Override
    public void afterPropertiesSet() throws Exception {
        init(); //메소드 최초 호출시키기
    }

    public void init() {
        initUser();
        initThread();
    }

    public void initUser(){
        roleService.createRole("ROLE_USER");
        roleService.createRole("ROLE_MANAGER");
        roleService.createRole("ROLE_ADMIN");

        accountService.createCommonUser(new AccountDto("user", "1111"));
        accountService.createUser(new AccountDto("manager", "1111"), "ROLE_MANAGER");
        accountService.createUser(new AccountDto("admin", "1111"), "ROLE_ADMIN");
    }

    public void initThread(){
        for (int i = 1; i <= 17; i++){
            threadService.createThread(String.valueOf(i) + "번째 글입니다.");
        }
    }
}
