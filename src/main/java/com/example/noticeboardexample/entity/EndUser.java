package com.example.noticeboardexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "end_user")
public class EndUser extends BaseEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "user_password")
  private String userPassword;

  @Column(name = "authority")
  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Builder
  private EndUser(String username, String userPassword, Authority authority) {
    this.username = username;
    this.userPassword = userPassword;
    this.authority = authority;
  }
}
