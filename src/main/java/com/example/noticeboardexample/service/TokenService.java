package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.Authority;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private final JwtEncoder encoder;

  public TokenService(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  public String generateToken(String username, Authority authority) {
    Instant now = Instant.now();
    String scope = authority.name();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(username)
        .claim("scope", scope)
        .build();

    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
