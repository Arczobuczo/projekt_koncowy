package pl.sda.borat.projekt_koncowy.reposytory;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.borat.projekt_koncowy.entity.PostCommentEntity;

import java.util.List;

@Repository
public interface PostCommentEntityRepository extends JpaRepository<PostCommentEntity, Long> {
    List<PostCommentEntity> findAllByMeetingEntityId(Long meetingId, Sort sort);
}
