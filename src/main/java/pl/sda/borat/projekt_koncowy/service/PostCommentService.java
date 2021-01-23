package pl.sda.borat.projekt_koncowy.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.borat.projekt_koncowy.dtos.PostInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.entity.PostCommentEntity;
import pl.sda.borat.projekt_koncowy.exeception.PostIdDoesntExistException;
import pl.sda.borat.projekt_koncowy.reposytory.MeetingEntityRepository;
import pl.sda.borat.projekt_koncowy.reposytory.PostCommentEntityRepository;
import pl.sda.borat.projekt_koncowy.reposytory.UserEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCommentService {

    private final PostCommentEntityRepository postCommentEntityRepository;
    private final MeetingEntityRepository meetingEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserContextService userContextService;


    public PostCommentService(PostCommentEntityRepository postCommentEntityRepository,
                              MeetingEntityRepository meetingEntityRepository,
                              UserEntityRepository userEntityRepository, UserContextService userContextService) {
        this.postCommentEntityRepository = postCommentEntityRepository;
        this.meetingEntityRepository = meetingEntityRepository;
        this.userEntityRepository = userEntityRepository;

        this.userContextService = userContextService;
    }

    @Transactional
    public void addNewPostCommentToMeeting(NewMeetingPostCommentForm newMeetingPostCommentForm, Long meetingID) {

        String email = userContextService.getCurrentlyLoggedUserEmail();

        final PostCommentEntity postCommentEntity = new PostCommentEntity();

        postCommentEntity.setCommentBody(newMeetingPostCommentForm.getCommentBody());
        postCommentEntity.setUserEntity(userEntityRepository.findUserEntityByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email)));
        postCommentEntity.setMeetingEntity(meetingEntityRepository.findById(meetingID)
                .orElseThrow(() -> new PostIdDoesntExistException(meetingID)));

        postCommentEntityRepository.save(postCommentEntity);
    }

    public List<PostInfoDto> getAllPostToMeeting(Long meetingID) {

        return postCommentEntityRepository.
                findAllByMeetingEntityId(meetingID, Sort.by("added").descending())
                .stream()
                .map(postCommentEntity ->
                        new PostInfoDto(
                                postCommentEntity.getUserEntity().getEmail(),
                                postCommentEntity.getCommentBody(),
                                postCommentEntity.getAdded()))
                .collect(Collectors.toList());
    }
}
