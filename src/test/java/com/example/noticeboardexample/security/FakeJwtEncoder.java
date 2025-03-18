package com.example.noticeboardexample.security;

import com.example.noticeboardexample.util.TestDataUtil;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

public class FakeJwtEncoder implements JwtEncoder {

  private final NimbusJwtEncoder nimbusJwtEncoder;

  public FakeJwtEncoder() {
    KeyPair rsaKeys = TestDataUtil.generateKeyPair();
    JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic()).privateKey((RSAPrivateKey) rsaKeys.getPrivate()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    this.nimbusJwtEncoder = new NimbusJwtEncoder(jwks);
  }

  public FakeJwtEncoder(NimbusJwtEncoder nimbusJwtEncoder) {
    this.nimbusJwtEncoder = nimbusJwtEncoder;
  }

  @Override
  public Jwt encode(JwtEncoderParameters parameters) throws JwtEncodingException {
    return this.nimbusJwtEncoder.encode(parameters);
  }
}
