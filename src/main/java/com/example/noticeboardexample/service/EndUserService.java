package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.Authority;
import com.example.noticeboardexample.entity.EndUser;
import com.example.noticeboardexample.repository.EndUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndUserService {

  private final EndUserRepository endUserRepository;

  public void signUp(String username, String password) {
    validateUsername(username);
    validatePassword(password);

    Optional<EndUser> exist = endUserRepository.findByUsername(username);
    if (exist.isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 username입니다.");
    }

    EndUser endUser = EndUser.builder()
        .username(username)
        .userPassword(password)
        .authority(Authority.ROLE_USER)
        .build();
    endUserRepository.save(endUser);
  }

  private void validateUsername(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("username이 비어있습니다.");
    }

    boolean userNamePatternMatched = username.matches("[a-z0-9]{4,10}$");
    if (!userNamePatternMatched) {
      throw new IllegalArgumentException("username은 소문자 알파벳과 숫자로 4자 이상 10자 이하여야 합니다.");
    }
  }

  private void validatePassword(String password) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("password가 비어있습니다.");
    }

    boolean passwordPatternMatched = password.matches("[a-zA-Z0-9!@#$%^&*]{8,15}$");
    if (!passwordPatternMatched) {
      throw new IllegalArgumentException("password는 대소문자 알파벳과 숫자로 8자 이상 15자 이하여야 합니다.");
    }
  }
}
