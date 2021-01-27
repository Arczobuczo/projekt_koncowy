package pl.sda.borat.projekt_koncowy.reposytory;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.borat.projekt_koncowy.entity.MeetingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingEntityRepository extends JpaRepository<MeetingEntity, Long> {

    List<MeetingEntity> findAllByTitleContainingAndToDateAfter(String title, LocalDateTime dateTime, Sort sort);

    List<MeetingEntity> findAllByTitleContaining(String title, Sort sort);

    List<MeetingEntity> findAllByToDateAfter(LocalDateTime now, Sort sort);

    List<MeetingEntity> findAllByTitleContainingAndSinceDateAfter(String title, LocalDateTime dateTime, Sort sort);

    Optional<MeetingEntity> findById(Long id);

    boolean existsByIdAndRegisterUserEntityForMeetingEntityEmail(Long userID, String userEmail);

    List<MeetingEntity> findAllByRegisterUserEntityForMeetingEntityEmail(String userEmail);
}
