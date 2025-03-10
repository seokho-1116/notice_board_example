package com.example.noticeboardexample;

import com.example.noticeboardexample.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class NoticeboardexampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(NoticeboardexampleApplication.class, args);
  }

}
