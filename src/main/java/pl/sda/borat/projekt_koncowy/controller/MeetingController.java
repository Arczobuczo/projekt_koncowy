package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.borat.projekt_koncowy.dtos.MeetingInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingForm;
import pl.sda.borat.projekt_koncowy.service.MeetingService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class MeetingController {

    private static final String MODEL_KEY = "meetings";
    private static final String NAME_PAGE_VIEW = "showMeeting/allMeetingPage";

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
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

    @PostMapping("/meeting-conteingin")
    public String getMeetingContainingByTitle(@RequestParam String search,
                                              Model model){

        List<MeetingShortInfoDto> meetingContaining = meetingService.getMeetingContaining(search);

        model.addAttribute(MODEL_KEY, meetingContaining);

        return NAME_PAGE_VIEW;
    }

    @GetMapping("/all-meeting")
    public String getAllMeeting(Model model){

        model.addAttribute(MODEL_KEY, meetingService.getAllMeeting());

        return NAME_PAGE_VIEW;
    }


    @GetMapping("/present-future-meeting")
    public String getMeetingPresent(Model model){

        List<MeetingShortInfoDto> meetingBetween = meetingService.getCurrentAndFutureMeeting();

        model.addAttribute(MODEL_KEY, meetingBetween);

        return NAME_PAGE_VIEW;
    }

    @GetMapping("/future-meeting")
    public String getFutureMeeting(Model model){

        List<MeetingShortInfoDto> futureMeeting = meetingService.getFutureMeeting();

        model.addAttribute(MODEL_KEY, futureMeeting);

        return NAME_PAGE_VIEW;
    }
    @GetMapping("/meeting/{meetingID}")
    public String getFullMeeting(@PathVariable Long meetingID,
                                 Model model){

        MeetingInfoDto allInformationMeeting = meetingService.getAllInformationMeeting(meetingID);

        model.addAttribute("meeting", allInformationMeeting);

        return "showMeeting/showFullInfoMeetingPage";
    }

}
