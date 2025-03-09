package com.example.noticeboardexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@MappedSuperclass
public class BaseEntity {

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private String createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private String updatedAt;
}
