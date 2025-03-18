package com.example.noticeboardexample.config;

import com.example.noticeboardexample.repository.FakeEndUserRepository;
import com.example.noticeboardexample.security.FakeJwtEncoder;
import com.example.noticeboardexample.service.EndUserService;
import com.example.noticeboardexample.service.TokenService;
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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@TestConfiguration
@Import({SecurityConfig.class})
public class RestControllerAuthTestConfiguration {

  @Bean
  @Primary
  public RsaKeyProperties rsaKeyProperties() {
    KeyPair keyPair = TestDataUtil.generateKeyPair();
    return new RsaKeyProperties((RSAPublicKey) keyPair.getPublic(),
        (RSAPrivateKey) keyPair.getPrivate());
  }

  @Bean
  public EndUserService endUserService() {
    JWK jwk = new RSAKey.Builder(rsaKeyProperties().publicKey()).privateKey(rsaKeyProperties().privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(jwks);

    return new EndUserService(new FakeEndUserRepository(), new TokenService(new FakeJwtEncoder(nimbusJwtEncoder)),
        PasswordEncoderFactories.createDelegatingPasswordEncoder());
  }
}
