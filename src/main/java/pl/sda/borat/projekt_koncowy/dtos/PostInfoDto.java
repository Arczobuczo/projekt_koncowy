package pl.sda.borat.projekt_koncowy.dtos;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostInfoDto {

    private final String email;

    private final String commentBody;

    private final LocalDateTime added;

    public PostInfoDto(String email, String commentBody, LocalDateTime added) {
        this.email = email;
        this.commentBody = commentBody;
        this.added = added;
    }
}
