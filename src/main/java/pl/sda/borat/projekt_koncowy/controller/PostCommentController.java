package pl.sda.borat.projekt_koncowy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.borat.projekt_koncowy.dtos.ErrorMessageBindingResult;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.service.PostCommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PostCommentController {

    private final PostCommentService postCommentService;
//    private static final String ERROR_PARAMETER = "?err=";

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @PostMapping("/meeting/{meetingId}/comment/add")
    public String addNewPostComment(@PathVariable Long meetingId,
                                    @ModelAttribute @Valid
                                    NewMeetingPostCommentForm newMeetingPostCommentForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            List<ErrorMessageBindingResult>  listOfErrors = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> new ErrorMessageBindingResult(
                            fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
            log.error("Errors from biding result {}", listOfErrors);

            redirectAttributes.addFlashAttribute("listOfErrors", listOfErrors);
            return "redirect:/meeting/" + meetingId;
        }

        postCommentService.addNewPostCommentToMeeting(newMeetingPostCommentForm, meetingId);


        return "redirect:/meeting/" + meetingId;
    }
}
