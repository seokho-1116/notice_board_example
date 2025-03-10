package com.example.noticeboardexample.repository;

import com.example.noticeboardexample.entity.EndUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndUserRepository extends JpaRepository<EndUser, Long> {

  Optional<EndUser> findByUsername(String username);
}
