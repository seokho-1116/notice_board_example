package com.example.noticeboardexample.config;

import com.example.noticeboardexample.controller.AuthController;
import com.example.noticeboardexample.repository.FakeEndUserRepository;
import com.example.noticeboardexample.service.EndUserService;
import com.example.noticeboardexample.service.TokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({SecurityConfig.class, AuthController.class, TokenService.class, EndUserService.class,
    FakeEndUserRepository.class})
public class RestControllerAuthTestConfiguration {

}
