package pl.sda.borat.projekt_koncowy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    public RoleEntity(String roleName) {
        this.roleName = roleName;
    }

    public RoleEntity() {
    }
}
