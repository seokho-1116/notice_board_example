package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.EndUser;
import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.repository.EndUserRepository;
import com.example.noticeboardexample.repository.PostRepository;
import com.example.noticeboardexample.service.dto.PostCreateDtoV2;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceV2 {

  private final PostRepository postRepository;
  private final EndUserRepository endUserRepository;

  public List<Post> findAll() {
    return postRepository.findAllByOrderByCreatedAtDesc();
  }

  public Post findById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  @Transactional
  public Post save(PostCreateDtoV2 postCreateDto) {
    EndUser endUser = endUserRepository.findByUsername(postCreateDto.username())
        .orElseThrow(NoSuchElementException::new);

    Post post = Post.builder()
        .title(postCreateDto.title())
        .content(postCreateDto.content())
        .build();
    endUser.addPost(post);

    return post;
  }

  @Transactional
  public Long deleteById(Long id, String username) {
    Post post = postRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

    post.validateEndUser(username);

    postRepository.deleteById(id);
    return id;
  }

  @Transactional
  public Post updateContent(Long id, String username, String content) {
    Post post = postRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    post.validateEndUser(username);

    post.updateContent(content);
    return post;
  }
}
