package com.example.noticeboardexample.util;

import java.lang.reflect.Field;

public class TestDataUtil {

  private TestDataUtil() {

  }

  public static <T> void forceSetId(T entity, long id) {
    try {
      Field idField = entity.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(entity, id);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
