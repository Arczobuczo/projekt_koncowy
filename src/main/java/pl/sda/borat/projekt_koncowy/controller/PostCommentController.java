package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.service.PostCommentService;

import javax.validation.Valid;

@Controller
public class PostCommentController {

    private final PostCommentService postCommentService;

    public PostCommentController(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @PostMapping("/meeting/{meetingId}/comment/add")
    public String addNewPostComment(@PathVariable Long meetingId,
                                    @ModelAttribute @Valid
                                    NewMeetingPostCommentForm newMeetingPostCommentForm,
                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "showMeeting/showFullInfoMeetingPage";
        }

        postCommentService.addNewPostCommentToMeeting(newMeetingPostCommentForm, meetingId);


        return "redirect:/meeting/" + meetingId;
    }
}
