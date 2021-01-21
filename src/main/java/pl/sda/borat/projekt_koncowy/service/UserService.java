package pl.sda.borat.projekt_koncowy.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.borat.projekt_koncowy.dtos.request.NewUserForm;
import pl.sda.borat.projekt_koncowy.entity.RoleEntity;
import pl.sda.borat.projekt_koncowy.entity.UserEntity;
import pl.sda.borat.projekt_koncowy.exeception.EmailExistInDatabaseException;
import pl.sda.borat.projekt_koncowy.reposytory.RoleEntityRepository;
import pl.sda.borat.projekt_koncowy.reposytory.UserEntityRepository;

@Service
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleEntityRepository roleEntityRepository;

    private static final String ROLE_NAME = "ROLE_USER";

    public UserService(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder,
                       RoleEntityRepository roleEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleEntityRepository = roleEntityRepository;
    }


    @Transactional
    public void registerNewUser(NewUserForm newUserForm) {
        boolean isExistEmailInDatabase = userEntityRepository.existsByEmail(newUserForm.getEmail()); //check does userEntity exist in database
        if (isExistEmailInDatabase){
            throw new EmailExistInDatabaseException(newUserForm.getEmail());                        //if yes break/ throw exception
        }

        RoleEntity roleEntity = roleEntityRepository
                .findByRoleName(ROLE_NAME)
                .orElseGet(() -> new RoleEntity(ROLE_NAME));


        saveUserEntityWithRole(newUserForm, roleEntity);
    }

    private void saveUserEntityWithRole(NewUserForm newUserForm, RoleEntity roleEntity) {   //ctrl+alt+M to cut common part of code
        final UserEntity userEntity = new UserEntity();                     //created LocalDateAndTime

        userEntity.setEmail(newUserForm.getEmail());                        //fill userEntity all dates
        userEntity.setNickname(newUserForm.getNickname());
        userEntity.setPassword(passwordEncoder.encode(newUserForm.getPassword()));   //used Bcrypt to code password
        userEntity.addRole(roleEntity);                                     //add role for users -> ROEL_USER

        userEntityRepository.save(userEntity);
    }

}
