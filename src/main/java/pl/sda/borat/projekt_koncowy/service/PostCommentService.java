package pl.sda.borat.projekt_koncowy.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.borat.projekt_koncowy.dtos.PostInfoDto;
import pl.sda.borat.projekt_koncowy.dtos.request.NewMeetingPostCommentForm;
import pl.sda.borat.projekt_koncowy.entity.PostCommentEntity;
import pl.sda.borat.projekt_koncowy.exeception.PostIdDoesntExistException;
import pl.sda.borat.projekt_koncowy.reposytory.MeetingEntityRepository;
import pl.sda.borat.projekt_koncowy.reposytory.PostCommentEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCommentService {

    private final PostCommentEntityRepository postCommentEntityRepository;
    private final MeetingEntityRepository meetingEntityRepository;
    private final UserContextService userContextService;


    public PostCommentService(PostCommentEntityRepository postCommentEntityRepository,
                              MeetingEntityRepository meetingEntityRepository,
                              UserContextService userContextService) {
        this.postCommentEntityRepository = postCommentEntityRepository;
        this.meetingEntityRepository = meetingEntityRepository;

        this.userContextService = userContextService;
    }

    public void addNewPostCommentToMeeting(NewMeetingPostCommentForm newMeetingPostCommentForm, Long meetingID) {

        final PostCommentEntity postCommentEntity = new PostCommentEntity();

        postCommentEntity.setCommentBody(newMeetingPostCommentForm.getCommentBody());
        postCommentEntity.setEmailUser(userContextService.getCurrentlyLoggedUserEmail());
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
                                postCommentEntity.getId(),
                                postCommentEntity.getEmailUser(),
                                postCommentEntity.getCommentBody(),
                                postCommentEntity.getAdded()))
                .collect(Collectors.toList());
    }
}