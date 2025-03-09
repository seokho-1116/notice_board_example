package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<Post, Long> {

  List<Post> findAll();

  Optional<Post> findById(Long id);

  Post save(Post post);

  void deleteById(Long id);
}
