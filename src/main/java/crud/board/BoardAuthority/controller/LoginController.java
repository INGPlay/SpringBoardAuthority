package crud.board.BoardAuthority.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping
    public String defaultPage(){
//        return "redirect:/login";
        return "redirect:/thread";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login/loginForm";
    }
}
