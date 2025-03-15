package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.EndUser;
import com.example.noticeboardexample.util.TestDataUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeEndUserRepository implements EndUserRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map<Long, EndUser> endUsers = new HashMap<>();

  @Override
  public Optional<EndUser> findByUsername(String username) {
    return endUsers.values().stream()
        .filter(endUser -> endUser.getUsername().equals(username))
        .findAny();
  }

  @Override
  public EndUser save(EndUser endUser) {
    long id = idGenerator.getAndIncrement();

    TestDataUtil.forceSetId(endUser, id);

    return endUsers.put(id, endUser);
  }
}
