package com.parazitik.graduatework.Repository;

import com.parazitik.graduatework.Entity.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface StatisticRepository extends CrudRepository<Statistic, Long> {
    @Query("SELECT COUNT(s) FROM Statistic s WHERE DATE(s.completeTask) = :date AND s.userId = :userId")
    Long countTasksCompletedOnDate(@Param("date") Date date, @Param("userId") Long userId);

    // Сколько задач создано в конкретную дату
    @Query("SELECT COUNT(s) FROM Statistic s WHERE DATE(s.createTask) = :date AND s.userId = :userId")
    Long countTasksCreatedOnDate(@Param("date") Date date, @Param("userId") Long userId);

    // Сколько задач создано
    @Query("SELECT COUNT(s) FROM Statistic s WHERE s.createTask IS NOT NULL AND s.userId = :userId")
    Long countAllCreatedTasks(@Param("userId") Long userId);

    // Количество выполненных подзадач в конкретный день
    @Query("SELECT COUNT(s) FROM Statistic s WHERE DATE(s.completeSubtask) = :date AND s.userId = :userId")
    Long countSubtasksCompletedOnDate(@Param("date") Date date, @Param("userId") Long userId);

}
