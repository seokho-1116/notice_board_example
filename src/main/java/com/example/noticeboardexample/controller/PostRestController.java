package com.example.noticeboardexample.controller;

import com.example.noticeboardexample.controller.request.PostCreateRequest;
import com.example.noticeboardexample.controller.request.PostDeleteRequest;
import com.example.noticeboardexample.controller.request.PostUpdateRequest;
import com.example.noticeboardexample.controller.response.PostResponseDto;
import com.example.noticeboardexample.controller.response.ResponseWrapper;
import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.service.PostService;
import com.example.noticeboardexample.service.dto.PostCreateDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

  private final PostService postService;

  @GetMapping
  public ResponseWrapper<List<PostResponseDto>> findAll() {
    List<Post> posts = postService.findAll();

    List<PostResponseDto> response = PostResponseDto.fromPosts(posts);
    return new ResponseWrapper<>(response);
  }

  @GetMapping("/{id}")
  public ResponseWrapper<PostResponseDto> findById(@PathVariable Long id) {
    Post post = postService.findById(id);

    PostResponseDto response = PostResponseDto.fromPost(post);
    return new ResponseWrapper<>(response);
  }

  @PostMapping
  public ResponseWrapper<PostResponseDto> save(@RequestBody PostCreateRequest postCreateRequest) {
    PostCreateDto postCreateDto = postCreateRequest.toPostCreateDto();

    Post post = postService.save(postCreateDto);

    PostResponseDto response = PostResponseDto.fromPost(post);
    return new ResponseWrapper<>(response);
  }

  @DeleteMapping("/{id}")
  public ResponseWrapper<Long> deleteById(@PathVariable Long id, @RequestBody PostDeleteRequest postDeleteRequest) {
    Long deletedPostId = postService.deleteById(id, postDeleteRequest.password());

    return new ResponseWrapper<>(deletedPostId);
  }

  @PatchMapping("/{id}/content")
  public ResponseWrapper<PostResponseDto> updateContent(@PathVariable Long id,
      @RequestBody PostUpdateRequest postUpdateRequest) {
    Post post = postService.updateContent(id, postUpdateRequest.password(),
        postUpdateRequest.content());

    PostResponseDto response = PostResponseDto.fromPost(post);
    return new ResponseWrapper<>(response);
  }
}
