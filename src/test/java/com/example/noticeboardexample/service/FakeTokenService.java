package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.Authority;
import com.example.noticeboardexample.security.FakeJwtEncoder;

public class FakeTokenService extends TokenService {

  public FakeTokenService() {
    super(new FakeJwtEncoder());
  }

  public String generateToken(String username, Authority authority) {
    return super.generateToken(username, authority);
  }
}
