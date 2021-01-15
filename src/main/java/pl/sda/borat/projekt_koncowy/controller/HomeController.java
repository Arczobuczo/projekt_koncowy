package pl.sda.borat.projekt_koncowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.service.MeetingService;

import java.util.List;

@Controller
public class HomeController {

    private final MeetingService meetingService;

    public HomeController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/")
    public String homePage(Model model){

        List<MeetingShortInfoDto> meetingShortInfoDtos = meetingService.getCurrentAndFutureMeeting();
        model.addAttribute("meetings", meetingShortInfoDtos);

        return "home/homePage";
    }
}
