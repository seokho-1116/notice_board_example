package com.example.noticeboardexample.controller;

import com.example.noticeboardexample.controller.request.PostCreateRequestV2;
import com.example.noticeboardexample.controller.request.PostUpdateRequestV2;
import com.example.noticeboardexample.controller.response.PostResponseDto;
import com.example.noticeboardexample.controller.response.ResponseWrapper;
import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.service.PostServiceV2;
import com.example.noticeboardexample.service.dto.PostCreateDtoV2;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostRestControllerV2 {

  private final PostServiceV2 postService;

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
  public ResponseWrapper<PostResponseDto> save(@RequestBody PostCreateRequestV2 postCreateRequest,
      Authentication authentication) {
    PostCreateDtoV2 postCreateDto = postCreateRequest.toPostCreateDto(authentication.getName());

    Post post = postService.save(postCreateDto);

    PostResponseDto response = PostResponseDto.fromPost(post);
    return new ResponseWrapper<>(response);
  }

  @DeleteMapping("/{id}")
  public ResponseWrapper<Long> deleteById(@PathVariable Long id,
      Authentication authentication
  ) {
    Long deletedPostId = postService.deleteById(id, authentication.getName());

    return new ResponseWrapper<>(deletedPostId);
  }

  @PatchMapping("/{id}/content")
  public ResponseWrapper<PostResponseDto> updateContent(@PathVariable Long id,
      Authentication authentication,
      @RequestBody PostUpdateRequestV2 postUpdateRequest) {
    Post post = postService.updateContent(id, authentication.getName(),
        postUpdateRequest.content());

    PostResponseDto response = PostResponseDto.fromPost(post);
    return new ResponseWrapper<>(response);
  }
}
