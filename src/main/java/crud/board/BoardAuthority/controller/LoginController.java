package crud.board.BoardAuthority.controller;

import crud.board.BoardAuthority.domain.dto.AccountDto;
import crud.board.BoardAuthority.domain.form.RegisterForm;
import crud.board.BoardAuthority.domain.form.UserInfoForm;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.security.authenticationManager.AccountContext;
import crud.board.BoardAuthority.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    @GetMapping
    public String defaultPage(){
//        return "redirect:/login";
        return "redirect:/thread";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login/loginForm";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("registerForm", new RegisterForm("", "", ""));

        return "login/registerForm";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        log.info("errors = {}", bindingResult);

        // Validation
        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())){
            bindingResult.reject("passwordCheck", null, "비밀번호 확인이 맞지 않습니다.");
            return "login/registerForm";
        }

        if (bindingResult.hasErrors()){
            return "login/registerForm";
        }

        // Service
        AccountDto accountDto = new AccountDto(registerForm.getUsername(), registerForm.getPassword());
        boolean isCreated = accountService.createCommonUser(accountDto);

        // Looting
        // 쿼리 스트링 추가
        redirectAttributes.addAttribute("isCreated", isCreated);

        if (isCreated){
            return "redirect:/login";
        } else {
            bindingResult.reject("serviceError", null, "다시 시도해주세요.");
            return "login/registerForm";
        }
    }


    // 얘는 권한으로 막는다.
    @GetMapping("/account")
    public String accountInfo(@AuthenticationPrincipal AccountContext accountContext,
                              Model model){
        model.addAttribute("accountInfo",
                accountService.findAccountInfoByUsername(accountContext.getUsername())
        );

        model.addAttribute("userInfoForm",
                new UserInfoForm("", "")
        );

        return "login/accountInfo";
    }

    @PostMapping("/account")
    public String updateAccountInfo(@Valid @ModelAttribute UserInfoForm userInfoForm,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal AccountContext accountContext,
                                    Model model,
                                    RedirectAttributes redirectAttributes){

        // 기본 정보
        model.addAttribute("accountInfo",
                accountService.findAccountInfoByUsername(accountContext.getUsername())
        );

        // Validation
        if (!userInfoForm.getPassword().equals(userInfoForm.getPasswordCheck())){
            bindingResult.reject("passwordCheck", null, "비밀번호 확인이 맞지 않습니다.");
            return "login/accountInfo";
        }

        // @AuthenticationPrincipal으로 받아온 정보는 DB에서 갱신되지 않으므로, 직접 DB에서 정보를 가져온다.
        if (accountService.isMatchedPassword(accountContext.getUsername(), userInfoForm.getPassword())){
            bindingResult.reject("passwordDuplicated", null, "이미 사용중인 비밀번호와 같습니다.");
            return "login/accountInfo";
        }

        if (bindingResult.hasErrors()){
            return "login/accountInfo";
        }

        accountService.updatePassword(accountContext.getUsername(), userInfoForm.getPassword());
        redirectAttributes.addAttribute("isUpdated", true);

        return "redirect:/account";
    }
}
