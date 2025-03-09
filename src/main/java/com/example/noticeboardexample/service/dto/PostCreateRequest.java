package com.example.noticeboardexample.service.dto;

public record PostCreateRequest(
    String writerName,
    String password,
    String title,
    String content
) {

}
