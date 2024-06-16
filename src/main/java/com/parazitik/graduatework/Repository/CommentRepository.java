package com.parazitik.graduatework.Repository;

import com.parazitik.graduatework.Entity.Comments;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comments, Long> {
}
