package pl.sda.borat.projekt_koncowy.dtos.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewMeetingPostCommentForm {

    private Long id;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 5, max = 500, message = "Comment can be min 5 max 500 character.")
    private String commentBody;

    @Override
    public String toString() {
        return "NewMeetingPostCommentForm{" +
                "commentBody='" + commentBody + '\'' +
                '}';
    }
}
