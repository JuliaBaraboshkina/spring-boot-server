package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.Subtasks;
import com.parazitik.graduatework.Repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    public Subtasks createSubtask(Subtasks subtask) {
        return subtaskRepository.save(subtask);
    }

    public boolean toggleSubtask(Long subtaskId) {
        Subtasks subtask = subtaskRepository.findById(subtaskId).orElse(null);

        assert subtask != null;

        subtask.setStatus(!subtask.isStatus());

        subtaskRepository.save(subtask);

        return subtask.isStatus();
    }
}
