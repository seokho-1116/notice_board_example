package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.EndUser;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface EndUserRepository extends Repository<EndUser, Long> {

  Optional<EndUser> findByUsername(String username);

  EndUser save(EndUser endUser);
}
