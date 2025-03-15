package com.example.noticeboardexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "writer_name")
  private String writerName;

  @Column(name = "post_password")
  private String postPassword;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @JoinColumn(name = "end_user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private EndUser endUser;

  @Builder
  private Post(String writerName, String postPassword, String title, String content) {
    this.writerName = writerName;
    this.postPassword = postPassword;
    this.title = title;
    this.content = content;
  }

  @Builder
  private Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void updateContent(String content) {
    this.content = content;
  }

  public void updateEndUser(EndUser endUser) {
    this.endUser = endUser;
  }

  public void validateEndUser(String username) {
    if (noPermission(username)) {
      throw new SecurityException("게시글에 대한 권한이 존재하지 않습니다.");
    }
  }

  private boolean noPermission(String username) {
    return this.endUser == null || !this.endUser.getUsername().equals(username);
  }

  public String getUsername() {
    return endUser == null ? writerName : endUser.getUsername();
  }
}
