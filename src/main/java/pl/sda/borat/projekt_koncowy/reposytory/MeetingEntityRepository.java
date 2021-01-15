package pl.sda.borat.projekt_koncowy.reposytory;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.borat.projekt_koncowy.entity.MeetingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingEntityRepository extends JpaRepository<MeetingEntity, Long> {

    List<MeetingEntity> findAllByToDateAfter(LocalDateTime now, Sort sort);

    List<MeetingEntity> findAllBySinceDateAfterOrderBySinceDateAsc(LocalDateTime now);

    List<MeetingEntity> findAllByTitleContainingOrderBySinceDateAsc(String title);

    List<MeetingEntity> findAllBySinceDateBeforeOrderBySinceDateAsc(LocalDateTime sinceDate);

    Optional<MeetingEntity> findById(Long id);
}
