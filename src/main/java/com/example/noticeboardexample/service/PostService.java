package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.repository.PostRepository;
import com.example.noticeboardexample.service.dto.PostCreateRequest;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post findById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  @Transactional
  public Post save(PostCreateRequest postCreateRequest) {
    Post post = Post.builder()
        .writerName(postCreateRequest.writerName())
        .password(postCreateRequest.password())
        .title(postCreateRequest.title())
        .content(postCreateRequest.content())
        .build();

    return postRepository.save(post);
  }

  @Transactional
  public Long deleteById(Long id, String password) {
    Post post = postRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

    validatePassword(password, post.getPassword());

    postRepository.deleteById(id);
    return id;
  }

  private void validatePassword(String inputPassword, String password) {
    if (inputPassword == null || !inputPassword.equals(password)) {
      throw new SecurityException("비밀번호가 일치하지 않습니다.");
    }
  }

  @Transactional
  public Post updateContent(Long id, String password, String content) {
    Post post = postRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    validatePassword(password, post.getPassword());

    post.updateContent(content);
    return post;
  }
}
