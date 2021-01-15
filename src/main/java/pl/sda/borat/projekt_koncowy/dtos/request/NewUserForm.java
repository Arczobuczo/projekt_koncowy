package pl.sda.borat.projekt_koncowy.dtos.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class NewUserForm {


    @NotBlank(message = "Can not be empty")
    @Email(message = "E-mail already exist in our service. Please use another one.")
    private String email;

    @Size(min = 8, max = 30, message = "Password must be min 8 and max 30 character.")
    @NotBlank(message = "Can not be empty")
    private String password;

    @NotBlank(message = "Nickname can't be contains only with whit spaces.")
    @Size(max = 50, message = "Nickname can be max 50 character.")
    private String nickname;

    @Override
    public String toString() {
        return "NewUserForm{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
