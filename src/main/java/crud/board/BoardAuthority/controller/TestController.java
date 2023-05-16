package crud.board.BoardAuthority.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    public String testUser(){
        return "user";
    }

    @GetMapping("/manager")
    public String testManager(){
        return "manager";
    }

    @GetMapping("/admin")
    public String testAdmin(){
        return "admin";
    }
}
