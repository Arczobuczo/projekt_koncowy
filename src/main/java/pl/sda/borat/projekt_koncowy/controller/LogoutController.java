package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-user")
    public String logOutUser(){
        return "redirect:/";
    }
}
