package crud.board.BoardAuthority.controller.admin;

import crud.board.BoardAuthority.domain.form.admin.RoleForm;
import crud.board.BoardAuthority.domain.form.admin.RolePathForm;
import crud.board.BoardAuthority.service.AccountService;
import crud.board.BoardAuthority.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // Role
    @GetMapping
    public String rolePage(Model model){

        addBaseAttributeForRoleList(model);
        return "admin/role";
    }

    @GetMapping("/{roleName}")
    public String viewRole(@PathVariable(name = "roleName") String roleName,
                           Model model){

        log.info("{}", String.join(", ", roleService.getRoutes(roleName)));

        RolePathForm rolePathForm = new RolePathForm(
                roleName, String.join(", ", roleService.getRoutes(roleName))
        );

        model.addAttribute("rolePathForm", rolePathForm);

        return "admin/role :: #roleInform";
    }

    @PostMapping
    public String createRole(@Validated @ModelAttribute RoleForm roleForm,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
            return "admin/role :: #roleList";
        }

        roleService.createRoleWithPrefix(roleForm.getRoleName());

        return renewRoleList(model);
    }

    @PutMapping
    public String updateRole(@Valid @ModelAttribute RolePathForm rolePathForm,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){

        String roleName = rolePathForm.getRoleName();
        String formattedRoutes = rolePathForm.getFormattedRoutes();

        log.info("{}", bindingResult);
        if (bindingResult.hasErrors()){
//            model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
//            return "admin/role";
            return "admin/role :: #roleInform";

        }

        // 갱신
        roleService.setPaths(roleName,
                List.of(formattedRoutes.split(","))
        );

        redirectAttributes.addAttribute("Success", true);
//        return "redirect:/admin/role/" + roleName;

        return renewRoleInform(model, roleName);
    }

    private String renewRoleInform(Model model, String roleName){
        RolePathForm rolePathForm = new RolePathForm(
                roleName, String.join(", ", roleService.getRoutes(roleName))
        );

        model.addAttribute("rolePathForm", rolePathForm);
        return "admin/role :: #roleInform";
    }

    private String renewRoleList(Model model){
        addBaseAttributeForRoleList(model);

        return "admin/role :: #roleList";
    }

    private void addBaseAttributeForRoleList(Model model){
        model.addAttribute("roleForm", new RoleForm(""));
        model.addAttribute("roleNames", roleService.getRoleNamesNotInAdmin());
    }
}
