package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.borat.projekt_koncowy.dtos.MeetingInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.PostInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingForm;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.service.MeetingService;
import pl.sda.borat.projekt_koncowy.service.PostCommentService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MeetingController {

    private static final String MODEL_KEY = "meetings";
    private static final String NAME_PAGE_VIEW = "showMeeting/allMeetingPage";

    private final MeetingService meetingService;
    private final PostCommentService postCommentService;

    public MeetingController(MeetingService meetingService, PostCommentService postCommentService) {
        this.meetingService = meetingService;
        this.postCommentService = postCommentService;
    }

    @GetMapping("/add-new-meeting")
    public String addNewMeetingFormForm(Model model){

        final NewMeetingForm newMeetingForm = new NewMeetingForm();
        model.addAttribute("newMeetingForm", newMeetingForm);

        return "meeting/addNewMeetingPage";
    }

    @PostMapping("/add-new-meeting")
    public String addNewMeetingFormSubit(@ModelAttribute @Valid
                                         NewMeetingForm newMeetingForm,
                                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "meeting/addNewMeetingPage";
        }

        meetingService.addNewMeeting(newMeetingForm);

        return "meeting/thankYouAddedNewMeetingPage";
    }

    @GetMapping("/meeting-search-title-conteingin-and-period")             //https://www.baeldung.com/spring-thymeleaf-request-parameters
    public String getMeetingContainingByTitle(@RequestParam String search,
                                              @RequestParam Short period,
                                              Model model){

        List<MeetingShortInfoDto> meetingContaining = meetingService.getFutureMeetingByTitleContaining(search, period);

        model.addAttribute(MODEL_KEY, meetingContaining);

        return NAME_PAGE_VIEW;
    }

    @GetMapping("/meeting/{meetingId}")
    public String getFullMeeting(@PathVariable Long meetingId,
                                 Model model){

        final NewMeetingPostCommentForm newMeetingPostCommentForm = new NewMeetingPostCommentForm();

        MeetingInfoDto allInformationMeeting = meetingService.getAllInformationMeeting(meetingId);

        List<PostInfoDto> postsInfo = postCommentService.getAllPostToMeeting(meetingId);

        model.addAttribute("newMeetingPostCommentForm", newMeetingPostCommentForm);
        model.addAttribute("meeting", allInformationMeeting);
        model.addAttribute("postsInfo", postsInfo);

        return "showMeeting/showFullInfoMeetingPage";
    }

}
