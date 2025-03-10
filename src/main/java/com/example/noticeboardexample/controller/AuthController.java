package com.example.noticeboardexample.controller;

import com.example.noticeboardexample.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

  private final TokenService tokenService;

  public AuthController(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping("/auth/token")
  public String token(Authentication authentication) {
    LOG.debug("Token requested by {}", authentication.getName());
    String token = tokenService.generateToken(authentication);
    LOG.debug("Token granted {}", token);
    return token;
  }
}
