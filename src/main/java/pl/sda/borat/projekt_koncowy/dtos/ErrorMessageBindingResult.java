package pl.sda.borat.projekt_koncowy.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessageBindingResult {
    private final String defaultMessage;

    public ErrorMessageBindingResult(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

}
