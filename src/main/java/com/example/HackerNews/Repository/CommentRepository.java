package com.example.HackerNews.Repository;

import com.example.HackerNews.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
