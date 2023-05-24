package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.dto.admin.UpdateAccountRoleDto;
import crud.board.BoardAuthority.domain.form.RolePathForm;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.domain.response.RolePathResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.PathService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Slf4j
@Controller
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

        model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());

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

    @PostMapping("/account/update-account")
    public String updateAccount(@RequestParam(name = "username") String username,
                                @RequestParam(name = "roleName") String roleName){

        log.info("{}, {}", username, roleName);
        UpdateAccountRoleDto updateAccountRoleDto = new UpdateAccountRoleDto();
        updateAccountRoleDto.setUsername(username);
        updateAccountRoleDto.setRoleName(roleName);

        accountService.updateAccountRole(username, roleName);
        return "redirect:/admin/account";
    }

    @PostMapping("/account/delete-account")
    public String deleteAccount(@RequestParam(name = "username") String username){

        accountService.deleteAccount(username);

        return "redirect:/admin/account";
    }

    // Role
    @GetMapping("/role")
    public String role(Model model){

        model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
        return "admin/role";
    }

    @GetMapping("/role/{roleName}")
    public String viewRole(@PathVariable(name = "roleName") String roleName,
                           Model model){

        RolePathResponse rolePathResponse = roleService.getRolePathResponse(roleName);

        model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
        model.addAttribute("rolePathResponse", rolePathResponse);

        return "admin/role";
    }

    @PostMapping("/role")
    public String updateRole(@RequestParam(name = "roleName") String roleName,
                             @RequestParam(name = "formattedRoute") String formattedRoute){
        roleService.setPaths(roleName,
                List.of(formattedRoute.split(","))
        );

        return "redirect:/admin/role/" + roleName;
    }
}
