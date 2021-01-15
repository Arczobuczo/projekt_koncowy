package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final String WRONG_LOGIN_OR_PASSWORD = "Wrong email or/and password, please try again.";

    @GetMapping("/login-user")
    public String loginUserForm(){

        return "user/loginUser";
    }


    @GetMapping("/login-user-error")
    public String invalidLogging(Model model){

        model.addAttribute("error", WRONG_LOGIN_OR_PASSWORD);

        return "user/loginUser";
    }
}
