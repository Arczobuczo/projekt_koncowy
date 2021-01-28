package pl.sda.borat.projekt_koncowy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.borat.projekt_koncowy.dtos.MeetingInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.PostInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.RegisterUserDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingForm;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.service.MeetingService;
import pl.sda.borat.projekt_koncowy.service.PostCommentService;
import pl.sda.borat.projekt_koncowy.service.UserContextService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class MeetingController {

    private static final String MODEL_KEY = "meetings";
    private static final String NAME_PAGE_VIEW = "showMeeting/allMeetingPage";

    private final MeetingService meetingService;
    private final PostCommentService postCommentService;
    private final UserContextService userContextService;

    public MeetingController(MeetingService meetingService, PostCommentService postCommentService, UserContextService userContextService) {
        this.meetingService = meetingService;
        this.postCommentService = postCommentService;
        this.userContextService = userContextService;
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

        List<MeetingShortInfoDto> meetingContaining = meetingService.getMeetingByTitleContainingWithPiriod(search, period);

        model.addAttribute(MODEL_KEY, meetingContaining);

        return NAME_PAGE_VIEW;
    }

    @GetMapping("/meeting/{meetingId}")
    public String getFullMeeting(@PathVariable Long meetingId,
                                 @RequestParam(required = false) String err,
                                 Model model){
        final String currentlyLogged = userContextService.getCurrentlyLoggedUserEmail();

        final NewMeetingPostCommentForm newMeetingPostCommentForm = new NewMeetingPostCommentForm();

        MeetingInfoDto allInformationMeeting = meetingService.getAllInformationMeeting(meetingId);

        List<PostInfoDto> postsInfo = postCommentService.getAllPostToMeeting(meetingId);

        List<RegisterUserDto> meetingForUser = meetingService.getAllRegisteredMeetingForUser(currentlyLogged);

        model.addAttribute("isRegisteredToMeeting", meetingService.isRegisteredToMeeting(
           meetingId, userContextService.getCurrentlyLoggedUserEmail()
        ));
        model.addAttribute("err", err);
        model.addAttribute("newMeetingPostCommentForm", newMeetingPostCommentForm);
        model.addAttribute("meeting", allInformationMeeting);
        model.addAttribute("postsInfo", postsInfo);
        model.addAttribute("meetingForUsers", meetingForUser);

        return "showMeeting/showFullInfoMeetingPage";
    }

    @PostMapping("/meeting/{meetingId}/register-user-for-meeting")                      //which is correctly? post or ret
    public String registerUserForMeeting(@PathVariable Long meetingId){

        meetingService.registerUserForMeeting(meetingId);

        return "redirect:/meeting/" + meetingId;
    }
    @GetMapping("/meeting/{meetingId}/unsubscribe-user-from-meeting")                   //which is correctly? post or get
    public String unsubscribeUserFormMeeting(@PathVariable Long meetingId){

        meetingService.unsubscribeUserFormMeeting(meetingId);

        return "redirect:/meeting/" + meetingId;

    }

}
