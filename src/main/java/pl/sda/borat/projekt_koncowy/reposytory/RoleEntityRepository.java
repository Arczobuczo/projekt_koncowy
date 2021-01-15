package pl.sda.borat.projekt_koncowy.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.borat.projekt_koncowy.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    boolean existsByRoleName(String roleName);

    RoleEntity findByRoleName(String roleName);
}
