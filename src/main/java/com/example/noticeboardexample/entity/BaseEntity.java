package com.example.noticeboardexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

  @Column(name = "created_at", updatable = false)
  private String createdAt;

  @Column(name = "updated_at")
  private String updatedAt;
}
