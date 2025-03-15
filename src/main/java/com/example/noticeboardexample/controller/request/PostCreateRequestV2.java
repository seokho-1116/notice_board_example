package com.example.noticeboardexample.controller.request;

import com.example.noticeboardexample.service.dto.PostCreateDtoV2;

public record PostCreateRequestV2(
    String title,
    String content
) {

  public PostCreateDtoV2 toPostCreateDto(String username) {
    return new PostCreateDtoV2(username, title, content);
  }
}
