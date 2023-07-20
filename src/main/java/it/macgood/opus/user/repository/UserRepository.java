package it.macgood.opus.user.repository;

import it.macgood.opus.user.model.Career;
import it.macgood.opus.user.model.Teams;
import it.macgood.opus.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    @Modifying
    @Transactional
    @Query("update _user as u set u.dateOfBirth = :bdate, u.email = :email, u.firstname = :firstname, u.lastname = :lastname, u.nickname = :nickname, u.teams = :teams, u.career = :career where u.id = :id")
    void updateUser(
            @Param("id") Integer id,
            @Param("bdate") String dateOfBirth,
            @Param("email") String email,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("nickname") String nickname,
            @Param("career") List<Career> career,
            @Param("teams") List<Teams> teams
    );

    @Modifying
    @Transactional
    @Query("update _user as u set u.photo = :photo where u.id = :userId")
    void updatePhoto(
            @Param("userId") Integer userId,
            @Param("photo") String photo
    );


}
