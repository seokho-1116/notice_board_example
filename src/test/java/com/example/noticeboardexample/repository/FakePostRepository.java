package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.util.TestDataUtil;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map<Long, Post> posts = new HashMap<>();

  @Override
  public Optional<Post> findById(Long id) {
    return Optional.ofNullable(posts.get(id));
  }

  @Override
  public Post save(Post post) {
    long id = idGenerator.getAndIncrement();

    Post saved = posts.put(id, post);
    TestDataUtil.forceSetId(post, id);

    return saved;
  }

  @Override
  public void deleteById(Long id) {
    posts.remove(id);
  }

  @Override
  public List<Post> findAllByOrderByCreatedAtDesc() {
    return posts.values()
        .stream()
        .sorted(Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }
}
