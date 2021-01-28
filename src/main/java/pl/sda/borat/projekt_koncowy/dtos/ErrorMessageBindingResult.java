package pl.sda.borat.projekt_koncowy.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorMessageBindingResult {
    private final String defaultMessage;

    public ErrorMessageBindingResult(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return "ErrorMessageBindingResult{" +
                "defaultMessage='" + defaultMessage + '\'' +
                '}';
    }
}
