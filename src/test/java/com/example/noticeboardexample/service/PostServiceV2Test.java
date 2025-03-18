package com.example.noticeboardexample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.noticeboardexample.entity.Authority;
import com.example.noticeboardexample.entity.EndUser;
import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.repository.EndUserRepository;
import com.example.noticeboardexample.repository.FakeEndUserRepository;
import com.example.noticeboardexample.repository.FakePostRepository;
import com.example.noticeboardexample.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostServiceV2Test {

  private final PostServiceV2 postServiceV2;
  private final EndUserRepository endUserRepository;
  private final PostRepository postRepository;

  PostServiceV2Test() {
    this.endUserRepository = new FakeEndUserRepository();
    this.postRepository = new FakePostRepository();
    this.postServiceV2 = new PostServiceV2(postRepository, endUserRepository);
  }

  @BeforeEach
  void init() {
    EndUser endUser = EndUser.builder()
        .authority(Authority.ROLE_USER)
        .username("testWriter")
        .userPassword("1234")
        .build();
    endUserRepository.save(endUser);

    Post post = Post.builder()
        .title("testTitle")
        .content("testContent")
        .build();
    post.updateEndUser(endUser);

    postRepository.save(post);
  }

  @DisplayName("이름이_일치할_때_게시글_삭제_테스트")
  @Test
  void testUpdateTestWhenPasswordMatch() {
    Post post = postServiceV2.findAll().getFirst();

    Long deletedId = postServiceV2.deleteById(post.getId(), "testWriter");

    assertThat(post.getId()).isEqualTo(deletedId);
  }

  @DisplayName("이름이_일치하지_않을_때_게시글_삭제_테스트")
  @Test
  void testUpdateTestWhenPasswordNotMatch() {
    Post post = postServiceV2.findAll().getFirst();

    Long postId = post.getId();

    assertThatThrownBy(() -> postServiceV2.deleteById(postId, "testWriter1234"))
        .isInstanceOf(SecurityException.class);
  }

  @DisplayName("이름이_일치할_때_게시글_업데이트_테스트")
  @Test
  void testDeleteTestWhenPasswordMatch() {
    Post post = postServiceV2.findAll().getFirst();

    Post updatedPost = postServiceV2.updateContent(post.getId(), "testWriter", "updatedContent");

    assertThat(updatedPost.getContent()).isEqualTo("updatedContent");
  }

  @DisplayName("이름이_일치하지_않을_때_게시글_업데이트_테스트")
  @Test
  void testDeleteTestWhenPasswordNotMatch() {
    Post post = postServiceV2.findAll().getFirst();

    Long postId = post.getId();

    assertThatThrownBy(() -> postServiceV2.updateContent(postId, "testWriter1234", "updatedContent"))
        .isInstanceOf(SecurityException.class);
  }
}