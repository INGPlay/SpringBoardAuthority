package crud.board.BoardAuthority.controller.admin;

import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.dto.admin.UpdateAccountRoleDto;
import crud.board.BoardAuthority.domain.form.RolePathForm;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.domain.response.RolePathResponse;
import crud.board.BoardAuthority.domain.response.pagingPost.PagingRange;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;
    private final RoleService roleService;

    // account
    @GetMapping("/account")
    public String accountList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) String searchOption,
                              @RequestParam(required = false) String searchWord,
                              Model model,
                              RedirectAttributes redirectAttributes){


        SearchDto searchDto = new SearchDto(searchWord, searchOption);
        Page<AccountInfoResponse> accountInfoResponses = accountService.search(
                searchDto, page, size
        );

        if (accountInfoResponses == null){
            return "admin/account";
        }

        boolean isInRange = checkPageRange(page, size, redirectAttributes, accountInfoResponses);
        if (!isInRange){
            redirectAttributes.addAttribute("searchOption", searchOption);
            redirectAttributes.addAttribute("searchWord", searchWord);
            return "redirect:/admin/account";
        }

        if (StringUtils.hasText(searchOption) && StringUtils.hasText(searchWord)){
            model.addAttribute("searchOption", searchOption);
            model.addAttribute("searchWord", searchWord);
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
            redirectAttributes.addAttribute("page", 1);
            redirectAttributes.addAttribute("size", size);
            return false;

            // 페이지가 최대 범위를 넘어선 경우
        } else if (page > pagingPost.getTotalPages()){
            redirectAttributes.addAttribute("page", pagingPost.getTotalPages());
            redirectAttributes.addAttribute("size", size);
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

        log.info("{}", String.join(", ", roleService.getRoutes(roleName)));

        RolePathForm rolePathForm = new RolePathForm(
                roleName, String.join(", ", roleService.getRoutes(roleName))
        );

        model.addAttribute("rolePathForm", rolePathForm);
        model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());

        return "admin/role";
    }

    @PostMapping("/role")
    public String updateRole(@Valid @ModelAttribute RolePathForm rolePathForm,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){

        String roleName = rolePathForm.getRoleName();
        String formattedRoutes = rolePathForm.getFormattedRoutes();

        log.info("{}", bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
            return "admin/role";
        }

        roleService.setPaths(roleName,
                List.of(formattedRoutes.split(","))
        );

        redirectAttributes.addAttribute("Success", true);
        return "redirect:/admin/role/" + roleName;
    }
}
