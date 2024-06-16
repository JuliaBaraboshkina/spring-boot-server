package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.Comments;
import com.parazitik.graduatework.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comments addComment(Long taskId, Comments comment) {
        return commentRepository.save(comment);
    }
}
