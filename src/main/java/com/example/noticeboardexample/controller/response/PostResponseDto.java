package com.example.noticeboardexample.controller.response;

import com.example.noticeboardexample.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record PostResponseDto(
    Long id,
    String writerName,
    String title,
    String content,
    LocalDateTime createdAt
) {

  public static List<PostResponseDto> fromPosts(List<Post> posts) {
    return posts.stream()
        .map(post -> PostResponseDto.builder()
            .id(post.getId())
            .writerName(post.getWriterName())
            .title(post.getTitle())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .build())
        .toList();
  }

  public static PostResponseDto fromPost(Post post) {
    return PostResponseDto.builder()
        .id(post.getId())
        .writerName(post.getWriterName())
        .title(post.getTitle())
        .content(post.getContent())
        .createdAt(post.getCreatedAt())
        .build();
  }
}
