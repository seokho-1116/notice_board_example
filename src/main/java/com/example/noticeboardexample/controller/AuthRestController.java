package com.example.noticeboardexample.controller;

import com.example.noticeboardexample.controller.request.SignUpRequest;
import com.example.noticeboardexample.controller.request.SignInRequest;
import com.example.noticeboardexample.controller.response.ResponseWrapper;
import com.example.noticeboardexample.service.EndUserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

  private final EndUserService endUserService;

  public AuthRestController(EndUserService endUserService) {
    this.endUserService = endUserService;
  }

  @PostMapping("/sign-up")
  public ResponseWrapper<String> signUp(@Valid @RequestBody SignUpRequest request) {
    endUserService.signUp(request.username(), request.password());

    return new ResponseWrapper<>("회원가입 성공");
  }

  @PostMapping("/sign-in")
  public ResponseWrapper<String> signIn(@Valid @RequestBody SignInRequest request) {
    String accessToken = endUserService.singIn(request.username(), request.password());

    return new ResponseWrapper<>(accessToken);
  }
}
