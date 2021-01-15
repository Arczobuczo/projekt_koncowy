package pl.sda.borat.projekt_koncowy.controller.globalModelAdvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.sda.borat.projekt_koncowy.service.UserContextService;

@ControllerAdvice
public class GlobalModelAdvice {

    final private UserContextService userContextService;

    public GlobalModelAdvice(UserContextService userContextService) {
        this.userContextService = userContextService;
    }
    @ModelAttribute("loggedAs")
    public String getLoggedAs(){
        return userContextService.getCurrentlyLoggedUserEmail();
    }
}
