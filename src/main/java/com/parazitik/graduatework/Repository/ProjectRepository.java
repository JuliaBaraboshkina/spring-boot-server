package com.parazitik.graduatework.Repository;

import com.parazitik.graduatework.Entity.Projects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Projects, Long> {
    @Query("SELECT p FROM Projects p LEFT JOIN p.members m WHERE p.ownerId = :userId OR m.id = :userId")
    List<Projects> findAllProjectsByMemberIdOrOwnerId(Long userId);
}
