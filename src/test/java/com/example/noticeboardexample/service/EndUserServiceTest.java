package com.example.noticeboardexample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.noticeboardexample.repository.FakeEndUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

class EndUserServiceTest {

  private final EndUserService endUserService;

  EndUserServiceTest() {
    this.endUserService = new EndUserService(new FakeEndUserRepository(), new FakeTokenService(),
        PasswordEncoderFactories.createDelegatingPasswordEncoder());
  }

  @BeforeEach
  void init() {
    endUserService.signUp("test", "12345678");
  }

  @DisplayName("회원가입_성공_테스트")
  @Test
  void testSignUpSuccess() {
    String username = "test1234";
    String password = "12345678";

    endUserService.signUp(username, password);

    assertThat(endUserService.singIn(username, password)).isNotNull();
  }

  @DisplayName("회원가입_아이디_중복_실패_테스트")
  @Test
  void testSignUpFailWithDuplicatedUsername() {
    String username = "test";
    String password = "12345678";

    assertThatThrownBy(() -> endUserService.signUp(username, password))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("로그인_성공_테스트")
  @Test
  void testSignInSuccess() {
    String username = "test";
    String password = "12345678";

    endUserService.singIn(username, password);

    String accessToken = endUserService.singIn(username, password);

    assertThat(accessToken).isNotNull();
  }

  @DisplayName("로그인_아이디_일치_실패_테스트")
  @Test
  void testSignInFailWithNotMatchedUsername() {
    String username = "test1";
    String password = "12345678";

    assertThatThrownBy(() -> endUserService.singIn(username, password))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("로그인_비밀번호_일치_실패_테스트")
  @Test
  void testSignInFailWithNotMatchedPassword() {
    String username = "test";
    String password = "123456789";

    assertThatThrownBy(() -> endUserService.singIn(username, password))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("로그인_아이디_입력_검증_실패_테스트")
  @ParameterizedTest
  @CsvSource(value = {
      "'', 12345678",
      ", 12345678",
      "t, 12345678",
      "te, 12345678",
      "tes, 12345678",
      "testtesttesttesttesttest, 12345678",
      "testtesttesttesttesttesttest, 12345678",
      "testtesttesttesttesttesttesttest, 12345678",
      "testtesttesttesttesttesttesttesttest, 12345678",
      "testtesttesttesttesttesttesttesttesttest, 12345678"
  }, delimiter = ',')
  void testSignInFailWithInvalidUsername(String username, String password) {
    assertThatThrownBy(() -> endUserService.singIn(username, password))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("로그인_비밀번호_입력_검증_실패_테스트")
  @ParameterizedTest
  @CsvSource(value = {"test, 1234567",
      "test,123456",
      "test,12345",
      "test,1234",
      "test,123",
      "test,12",
      "test,1",
      "test,''",
      "test,",
      "test,123456789",
      "test,1234567890",
      "test,12345678901",
      "test,12389123801298312038091283091203812908309128390128093019283"
  }, delimiter = ',')
  void testSignInFailWithInvalidPassword(String username, String password) {
    assertThatThrownBy(() -> endUserService.singIn(username, password))
        .isInstanceOf(IllegalArgumentException.class);
  }
}