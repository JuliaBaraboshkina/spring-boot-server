package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.Statistic;
import com.parazitik.graduatework.Entity.Users;
import com.parazitik.graduatework.Model.StatisticDTO;
import com.parazitik.graduatework.Repository.StatisticRepository;
import com.parazitik.graduatework.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private UserRepository userRepository;

    private Statistic generateStatistic(Long userId) {
        Statistic statistic = new Statistic();

        statistic.setUserId(userId);

        return statistic;
    }

    public void toggleSubtask(Long userId) {
        Statistic statistic = generateStatistic(userId);

        statistic.setCompleteTask(new Date());

        statisticRepository.save(statistic);
    }

    public void createProject(Long userId, boolean solo) {
        Statistic statistic = generateStatistic(userId);
        Users user = userRepository.findById(userId).orElse(null);

        if (user == null) return;

        statistic.setCreateProject(new Date());

        if (solo) user.setSoloProjectsCount(user.getSoloProjectsCount() + 1);
        else user.setTeamProjectsCount(user.getTeamProjectsCount() + 1);

        statisticRepository.save(statistic);
    }

    public void createTask(Long userId) {
        Statistic statistic = generateStatistic(userId);

        statistic.setCreateTask(new Date());

        statisticRepository.save(statistic);
    }

    public List<Long> getSubtasksCompletedInWeek(Date anyDateInWeek, Long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(anyDateInWeek);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        List<Date> weekDates = IntStream.range(0, 7)
                .mapToObj(i -> {
                    Calendar cal = (Calendar) calendar.clone();
                    cal.add(Calendar.DATE, i);
                    return cal.getTime();
                })
                .toList();

        return weekDates.stream()
                .map(date -> statisticRepository.countSubtasksCompletedOnDate(date, userId))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> getStatisticData(Long userId, Date date) {

        System.out.println(date);

        StatisticDTO statistic = StatisticDTO.builder()
                .taskCompleted(statisticRepository.countTasksCompletedOnDate(date, userId))
                .taskCreated(statisticRepository.countTasksCreatedOnDate(date, userId))
                .projectCreatedAllTime(statisticRepository.countAllCreatedTasks(userId))
                .soloProjects(userRepository.countSoloProjects(userId))
                .teamProjects(userRepository.countTeamProjects(userId))
                .taskCompletedByDays(getSubtasksCompletedInWeek(date, userId))
                .taskCreatedAllTime(statisticRepository.countAllCreatedTasks(userId))
                .build();

        System.out.println(statistic);

        return ResponseEntity.ok(statistic);
    }
}
