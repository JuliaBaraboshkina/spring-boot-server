package com.parazitik.graduatework.Repository;

import com.parazitik.graduatework.Entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.email = ?1")
    boolean findByEmail(String email);
    Users getByEmail(String email);
    Optional<Users> getUsersByEmailAndPassword(String login, String password);

    @Query("SELECT s.soloProjectsCount FROM Users s WHERE s.id = :userId")
    Integer countSoloProjects(@Param("userId") Long userId);

    @Query("SELECT s.teamProjectsCount FROM Users s WHERE s.id = :userId")
    Integer countTeamProjects(@Param("userId") Long userId);
}
