package pl.sda.borat.projekt_koncowy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.service.PostCommentService;

import javax.validation.Valid;

@Slf4j
@Controller
public class PostCommentController {

    private final PostCommentService postCommentService;
    private static final String ERROR_PARAMETER = "?err=";

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @PostMapping("/meeting/{meetingId}/comment/add")
    public String addNewPostComment(@PathVariable Long meetingId,
                                    @ModelAttribute @Valid
                                    NewMeetingPostCommentForm newMeetingPostCommentForm,
                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String defaultMessage = null;
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                defaultMessage = fieldError.getDefaultMessage();
            }

            return "redirect:/meeting/" + meetingId + ERROR_PARAMETER + defaultMessage;
        }

        postCommentService.addNewPostCommentToMeeting(newMeetingPostCommentForm, meetingId);


        return "redirect:/meeting/" + meetingId;
    }
}
