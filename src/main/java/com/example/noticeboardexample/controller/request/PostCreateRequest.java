package com.example.noticeboardexample.controller.request;

import com.example.noticeboardexample.service.dto.PostCreateDto;

public record PostCreateRequest(
    String writerName,
    String password,
    String title,
    String content
) {

  public PostCreateDto toPostCreateDto() {
    return new PostCreateDto(writerName(), password(), title(), content());
  }
}
