package pl.sda.borat.projekt_koncowy.api;

import org.springframework.web.bind.annotation.*;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MeetingRestController {

    private final MeetingService meetingService;

    public MeetingRestController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meetings")
    public List<MeetingShortInfoDto> getAllMeetings(@RequestParam() String search,
                                                    @RequestParam() Short period){
        return meetingService.getMeetingByTitleContainingWithPiriod(search, period);
    }

}
