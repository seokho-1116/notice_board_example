package com.example.noticeboardexample.service;

import com.example.noticeboardexample.entity.Post;
import com.example.noticeboardexample.repository.PostRepository;
import java.lang.reflect.Field;
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
    forceSetId(post, id);

    return saved;
  }

  private void forceSetId(Post post, long id) {
    try {
        Field idField = Post.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(post, id);
    } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
    }
}

  @Override
  public void deleteById(Long id) {
    posts.remove(id);
  }

  @Override
  public List<Post> findAllByOrderByCreatedAtDesc() {
    return posts.values()
        .stream()
        .sorted(Comparator.comparing(Post::getCreatedAt))
        .toList();
  }
}
