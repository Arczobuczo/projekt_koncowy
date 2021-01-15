package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpView {

    @GetMapping("/help")
    public String help(){
        return "help";
    }


}
