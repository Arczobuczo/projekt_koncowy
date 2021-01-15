package pl.sda.borat.projekt_koncowy.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.sda.borat.projekt_koncowy.exeception.EmailExistInDatabaseException;
import pl.sda.borat.projekt_koncowy.exeception.PostIdDoesntExistException;


@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailExistInDatabaseException.class)
    public String handle(EmailExistInDatabaseException e){

        log.info("Global exception handling for: {}", e.getMessage());

        return "user/emailExistInDatabase";
    }

    @ExceptionHandler(PostIdDoesntExistException.class)
    public String handle(PostIdDoesntExistException e){

        log.error("Global exception handling for: {}", e.getMessage());

        return "redirect;/";
    }

    @ExceptionHandler(Exception.class)
    public String handle(Exception e){
        log.error("Unknown exception: {}", e.getMessage());

        return "redirect:/";
    }

}
