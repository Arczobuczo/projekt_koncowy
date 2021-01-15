package pl.sda.borat.projekt_koncowy.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
public class NewMeetingForm {

    @NotBlank(message = "Can not be empty")
    @Size(max = 1000, message = "Title can be max 1000 character.")
    private String title;

    @NotNull(message = "Please add start date of meeting")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sinceDate;

    @NotNull(message = "Please add end date of meeting")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime toDate;

    @Size(min = 20, max = 999999, message = "Common body have to min 20 and max 999999 character ")
    private String body;

    @Override
    public String toString() {
        return "NewMeetingForm{" +
                "title='" + title + '\'' +
                ", startDate=" + sinceDate +
                ", endDate=" + toDate +
                ", body='" + body + '\'' +
                '}';
    }
}
