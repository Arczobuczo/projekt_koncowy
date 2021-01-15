package pl.sda.borat.projekt_koncowy.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MeetingInfoDto {

    private final Long id;

    private final String title;

    private final LocalDateTime sinceDate;

    private final LocalDateTime toDate;

    private final String body;

    public MeetingInfoDto(Long id, String title, LocalDateTime sinceDate, LocalDateTime toDate, String body) {
        this.id = id;
        this.title = title;
        this.sinceDate = sinceDate;
        this.toDate = toDate;
        this.body = body;
    }
}
