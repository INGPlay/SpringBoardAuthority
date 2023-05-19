package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.dto.admin.UpdateAccountRoleDto;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Component
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;
    private final RoleService roleService;

    // account
    @GetMapping("/account")
    public String accountList(@RequestParam(defaultValue = "1") int accountPage,
                              @RequestParam(defaultValue = "10") int accountSize,
                              Model model,
                              RedirectAttributes redirectAttributes){

        Page<AccountInfoResponse> accountInfoResponses = accountService.pageAccount(accountPage, accountSize);
        if (accountInfoResponses == null){
            return "admin/account";
        }

        boolean isInRange = checkPageRange(accountPage, accountSize, redirectAttributes, accountInfoResponses);
        if (!isInRange){
            return "redirect:/admin/account";
        }

        model.addAttribute("accountInfoResponses", accountInfoResponses);
        PagingRange<AccountInfoResponse> pagingRange = new PagingRange<>(accountInfoResponses, 10);

        model.addAttribute("pagingRange", pagingRange);

        model.addAttribute("roleNames", roleService.getRoleNames());

        return "admin/account";
    }

    private boolean checkPageRange(int page, int size, RedirectAttributes redirectAttributes, Page<AccountInfoResponse> pagingPost) {
        // 페이지가 1보다 작은 경우
        if (page < 1){
            redirectAttributes.addAttribute("accountPage", 1);
            redirectAttributes.addAttribute("accountSize", size);
            return false;

            // 페이지가 최대 범위를 넘어선 경우
        } else if (page > pagingPost.getTotalPages()){
            redirectAttributes.addAttribute("accountPage", pagingPost.getTotalPages());
            redirectAttributes.addAttribute("accountSize", size);
            return false;
        }

        return true;
    }

    @PostMapping("/update-account")
    public String updateAccount(@RequestParam(name = "username") String username,
                                @RequestParam(name = "roleName") String roleName){

        log.info("{}, {}", username, roleName);
        UpdateAccountRoleDto updateAccountRoleDto = new UpdateAccountRoleDto();
        updateAccountRoleDto.setUsername(username);
        updateAccountRoleDto.setRoleName(roleName);

        accountService.updateAccountRole(username, roleName);
        return "redirect:/admin/account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(@RequestParam(name = "username") String username){

        accountService.deleteAccount(username);

        return "redirect:/admin/account";
    }

    // Role
    @GetMapping("/role")
    public String role(){

        return "admin/role";
    }
}
