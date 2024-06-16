package com.parazitik.graduatework.Repository;

import com.parazitik.graduatework.Entity.Tasks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Tasks, Long> {
    @Query("SELECT t FROM Tasks t JOIN t.assignee a WHERE a.id = :userId")
    List<Tasks> findAllTasksByAssigneeId(Long userId);

    @Query("SELECT t FROM Tasks t JOIN t.assignee a WHERE a.id = :userId AND t.status = :status")
    List<Tasks> findAllTasksByAssigneeIdAndStatus(Long userId, String status);

    Optional<Tasks> findTasksById(Long taskId);

    List<Tasks> findTasksByProjectIdAndStatus(Long projectId, String status);
    List<Tasks> findTasksByProjectId(Long projectId);

    @Query("SELECT COUNT(s) FROM Tasks t JOIN t.subtasks s WHERE t.id = :taskId")
    Long countTotalSubtasksByTaskId(Long taskId);

    @Query("SELECT COUNT(s) FROM Tasks t JOIN t.subtasks s WHERE t.id = :taskId AND s.status = true")
    Long countCompletedSubtasksByTaskId(Long taskId);
}
