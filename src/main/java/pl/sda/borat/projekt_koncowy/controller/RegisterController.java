package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.borat.projekt_koncowy.dtos.request.NewUserForm;
import pl.sda.borat.projekt_koncowy.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

    final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/user-registration")
    public String userRegistrationForm(Model model){

        final NewUserForm newUserForm = new NewUserForm();
        model.addAttribute("newUserForm", newUserForm);

        return "user/userRegistration";
    }
    @PostMapping("/user/user-registration")
    public String userRegistrationSubmit(@ModelAttribute @Valid
                                         NewUserForm newUserForm,
                                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "user/userRegistration";
        }

        userService.registerNewUser(newUserForm);

        return "user/thankYouReigstrationPage";
    }
}
