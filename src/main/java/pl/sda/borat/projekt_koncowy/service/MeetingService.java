package pl.sda.borat.projekt_koncowy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.borat.projekt_koncowy.dtos.MeetingInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.MeetingShortInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.RegisterUserDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingForm;
import pl.sda.borat.projekt_koncowy.entity.MeetingEntity;
import pl.sda.borat.projekt_koncowy.entity.UserEntity;
import pl.sda.borat.projekt_koncowy.exeception.MeetingDoesntExistException;
import pl.sda.borat.projekt_koncowy.exeception.PostIdDoesntExistException;
import pl.sda.borat.projekt_koncowy.exeception.UserDoesntExistException;
import pl.sda.borat.projekt_koncowy.reposytory.MeetingEntityRepository;
import pl.sda.borat.projekt_koncowy.reposytory.UserEntityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MeetingService {

    private static final String SORT_BY_SINCE_DATE = "sinceDate";

    private final UserEntityRepository userEntityRepository;
    private final UserContextService userContextService;
    private final MeetingEntityRepository meetingEntityRepository;


    public MeetingService(UserEntityRepository userEntityRepository, UserContextService userContextService,
                          MeetingEntityRepository meetingEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.userContextService = userContextService;
        this.meetingEntityRepository = meetingEntityRepository;
    }


    @Transactional
    public void addNewMeeting(NewMeetingForm newMeetingForm) {

        final String email = userContextService.getCurrentlyLoggedUserEmail();

        final MeetingEntity meetingEntity = new MeetingEntity();

        meetingEntity.setTitle(newMeetingForm.getTitle());
        meetingEntity.setBody(newMeetingForm.getBody());
        meetingEntity.setSinceDate(newMeetingForm.getSinceDate());
        meetingEntity.setToDate(newMeetingForm.getToDate());
        meetingEntity.setUserEntity(userEntityRepository
                .findUserEntityByEmail(email)
        .orElseThrow(() -> new UserDoesntExistException(email)));

        meetingEntityRepository.save(meetingEntity);
    }

    public List<MeetingShortInfoDto> getCurrentAndFutureMeeting(){
        LocalDateTime currentTime = LocalDateTime.now();
        log.info("Get current Time {}",currentTime);

        return meetingEntityRepository.findAllByToDateAfter(currentTime, Sort.by(SORT_BY_SINCE_DATE).ascending())
                .stream()
                .map(this::convertMapToDto)
                .collect(Collectors.toList());
    }


    public List<MeetingShortInfoDto> getMeetingByTitleContainingWithPiriod(String title, Short period){
        LocalDateTime currentTime = LocalDateTime.now();
        log.info("Get current Time {}",currentTime);
        log.info("Get current period {}",period);

        switch (period) {
            case 1:
                return meetingEntityRepository
                        .findAllByTitleContainingAndSinceDateAfter(title, currentTime, Sort.by(SORT_BY_SINCE_DATE).ascending())
                        .stream()
                        .map(this::convertMapToDto)
                        .collect(Collectors.toList());
            case 2:
                return meetingEntityRepository
                        .findAllByTitleContainingAndToDateAfter(title, currentTime, Sort.by(SORT_BY_SINCE_DATE).ascending())
                        .stream()
                        .map(this::convertMapToDto)
                        .collect(Collectors.toList());
            case 3:
                return meetingEntityRepository
                        .findAllByTitleContaining(title, Sort.by(SORT_BY_SINCE_DATE).ascending())
                        .stream()
                        .map(this::convertMapToDto)
                        .collect(Collectors.toList());

            default:
                return List.of();
        }
    }

    private MeetingShortInfoDto convertMapToDto(MeetingEntity meetingEntity) {              //ctrl+alt+M to cut common part of code
        return new MeetingShortInfoDto(
                meetingEntity.getId(),
                meetingEntity.getTitle(),
                meetingEntity.getSinceDate(),
                meetingEntity.getToDate(),
                meetingEntity.getBody());
    }

    public MeetingInfoDto getAllInformationMeeting(Long meetingID) {

        return meetingEntityRepository.findById(meetingID)
                .map(meetingEntity -> new MeetingInfoDto(
                        meetingEntity.getId(),
                        meetingEntity.getTitle(),
                        meetingEntity.getSinceDate(),
                        meetingEntity.getToDate(),
                        meetingEntity.getBody()
                ))
                .orElseThrow(() -> new PostIdDoesntExistException(meetingID));

    }

    @Transactional
    public void registerUserForMeeting(Long meetingId) {
        final String email = userContextService.getCurrentlyLoggedUserEmail();

        final UserEntity userEntity = userEntityRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserDoesntExistException(email));

        final MeetingEntity meetingEntity = meetingEntityRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingDoesntExistException(meetingId));

        meetingEntity.signUserForMeetingEntity(userEntity);



    }

    @Transactional
    public void unsubscribeUserFormMeeting(Long meetingId){
        final String email = userContextService.getCurrentlyLoggedUserEmail();

        final UserEntity userEntity = userEntityRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserDoesntExistException(email));

        final MeetingEntity meetingEntity = meetingEntityRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingDoesntExistException(meetingId));

        meetingEntity.unsubscribeUserFromMeetingEntity(userEntity);

    }

    public boolean isRegisteredToMeeting(Long meetingId, String currentlyLoggedUser){
        return meetingEntityRepository.existsByIdAndRegisterUserEntityForMeetingEntityEmail(meetingId, currentlyLoggedUser);
    }

    @Transactional
    public List<RegisterUserDto> getAllRegisteredMeetingForUser(String currentlyLogedUsser){
        return meetingEntityRepository
                .findAllByRegisterUserEntityForMeetingEntityEmail(currentlyLogedUsser)
                .stream()
                .map(meetingEntity -> new RegisterUserDto(meetingEntity.getId(), meetingEntity.getTitle()))
                .collect(Collectors.toList());
    }
}
