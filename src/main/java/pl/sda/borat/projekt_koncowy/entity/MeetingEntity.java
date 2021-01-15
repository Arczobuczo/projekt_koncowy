package pl.sda.borat.projekt_koncowy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "meetings")
@Entity
public class MeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String title;

    private LocalDateTime sinceDate;

    private LocalDateTime toDate;

    @Column(length = 999999)
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)                  //https://kobietydokodu.pl/15-relacje-jeden-do-wielu-wiele-do-jednego/
    @JoinColumn(columnDefinition = "user_entity_id")
    private UserEntity userEntity;

}
