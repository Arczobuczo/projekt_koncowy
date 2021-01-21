package pl.sda.borat.projekt_koncowy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUser;
    
    @Column(length = 500)
    private String commentBody;

    private LocalDateTime added = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "meeting_entity_id")
    private MeetingEntity meetingEntity;
    
}
