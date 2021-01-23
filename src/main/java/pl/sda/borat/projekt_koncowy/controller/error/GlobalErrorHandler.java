package pl.sda.borat.projekt_koncowy.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.sda.borat.projekt_koncowy.exeception.EmailExistInDatabaseException;
import pl.sda.borat.projekt_koncowy.exeception.MeetingDoesntExistException;
import pl.sda.borat.projekt_koncowy.exeception.PostIdDoesntExistException;
import pl.sda.borat.projekt_koncowy.exeception.UserDoesntExistException;


@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailExistInDatabaseException.class)
    public String handle(EmailExistInDatabaseException e){

        log.info("Global exception handling for: {}", e.getMessage());

        return "error/emailExistInDatabase";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserDoesntExistException.class)
    public String handle(UserDoesntExistException e){

        log.error("Global exception handling for: {}", e.getMessage());


        return "error/userDoesntExist";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MeetingDoesntExistException.class)
    public String handle(MeetingDoesntExistException e){
        log.error("Global exception handling for: {}", e.getMessage());

        return "error/meetingDoesntExist";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostIdDoesntExistException.class)
    public String handle(PostIdDoesntExistException e){

        log.error("Global exception handling for: {}", e.getMessage());

        return "redirect;/";
    }

//    @ExceptionHandler(Exception.class)
//    public String handle(Exception e){
//        log.error("Unknown exception: {}", e.getMessage());
//
//        return "error/unknownError";
//    }

}
