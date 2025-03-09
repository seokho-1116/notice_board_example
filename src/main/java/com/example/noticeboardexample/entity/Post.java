package com.example.noticeboardexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Builder
  private Post(String writerName, String postPassword, String title, String content) {
    this.writerName = writerName;
    this.postPassword = postPassword;
    this.title = title;
    this.content = content;
  }

  public void updateContent(String content) {
    this.content = content;
  }
}
