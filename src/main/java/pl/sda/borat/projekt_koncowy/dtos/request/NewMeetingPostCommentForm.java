package pl.sda.borat.projekt_koncowy.dtos.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewMeetingPostCommentForm {

    private String emailUser;

    @NotNull(message = "Can not be empty")
    @Size(max = 500, message = "Comment can be max 500 character.")
    private String commentBody;


}
