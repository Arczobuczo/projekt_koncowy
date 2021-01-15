package pl.sda.borat.projekt_koncowy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(length = 50)
    private String nickname;

    private LocalDateTime added = LocalDateTime.now();  //data when user was registered (data generated with created new constructor)

    @ManyToMany(fetch = FetchType.LAZY)                                       //default setting fetch = FetchType.LAZY do if needed
    @JoinTable(name = "users_roles")
    private Set<RoleEntity> roles = new HashSet<>();    //unique roles in table RoleEntity

    public void addRole(RoleEntity role){
        roles.add(role);
    }

}
