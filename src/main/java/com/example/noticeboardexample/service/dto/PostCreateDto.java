package com.example.noticeboardexample.service.dto;

public record PostCreateDto(
    String writerName,
    String password,
    String title,
    String content
) {

}
