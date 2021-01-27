package pl.sda.borat.projekt_koncowy.dtos;

import lombok.Getter;

@Getter
public class RegisterUserDto {

    private final Long id;

    private final String title;

    public RegisterUserDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
