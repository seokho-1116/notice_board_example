package com.example.noticeboardexample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.repository.FakePostRepository;
import com.example.noticeboardexample.service.dto.PostCreateDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostServiceTest {

  private final PostService postService;

  PostServiceTest() {
    this.postService = new PostService(new FakePostRepository());
  }

  @BeforeEach
  void init() {
    PostCreateDto postCreateDto = new PostCreateDto("testWriter", "1234", "testTitle",
        "testContent");

    postService.save(postCreateDto);
  }

  @AfterEach
  void clear() {
    List<Post> allPosts = postService.findAll();

    for (Post post : allPosts) {
      postService.deleteById(post.getId(), "1234");
    }
  }

  @DisplayName("비밀번호가_일치할_때_게시글_삭제_테스트")
  @Test
  void testUpdateTestWhenPasswordMatch() {
    Post post = postService.findAll().getFirst();

    Long deletedId = postService.deleteById(post.getId(), "1234");

    assertThat(post.getId()).isEqualTo(deletedId);
  }

  @DisplayName("비밀번호가_일치하지_않을_때_게시글_삭제_테스트")
  @Test
  void testUpdateTestWhenPasswordNotMatch() {
    Post post = postService.findAll().getFirst();

    Long postId = post.getId();

    assertThatThrownBy(() -> postService.deleteById(postId, "test"))
        .isInstanceOf(SecurityException.class);
  }

  @DisplayName("비밀번호가_일치할_때_게시글_업데이트_테스트")
  @Test
  void testDeleteTestWhenPasswordMatch() {
    Post post = postService.findAll().getFirst();

    Post updatedPost = postService.updateContent(post.getId(), "1234", "updatedContent");

    assertThat(updatedPost.getContent()).isEqualTo("updatedContent");
  }

  @DisplayName("비밀번호가_일치하지_않을_때_게시글_업데이트_테스트")
  @Test
  void testDeleteTestWhenPasswordNotMatch() {
    Post post = postService.findAll().getFirst();

    Long postId = post.getId();

    assertThatThrownBy(() -> postService.updateContent(postId, "test", "updatedContent"))
        .isInstanceOf(SecurityException.class);
  }
}