package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
