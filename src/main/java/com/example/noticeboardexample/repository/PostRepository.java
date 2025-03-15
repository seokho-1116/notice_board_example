package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<Post, Long> {

  @Query("SELECT p FROM Post p LEFT JOIN FETCH p.endUser eu WHERE p.id = :id")
  Optional<Post> findById(Long id);

  Post save(Post post);

  void deleteById(Long id);

  @Query("SELECT p FROM Post p LEFT JOIN FETCH p.endUser eu ORDER BY p.createdAt DESC")
  List<Post> findAllByOrderByCreatedAtDesc();
}
