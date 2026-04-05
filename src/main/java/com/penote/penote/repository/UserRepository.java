package com.penote.penote.repository;

import com.penote.penote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // String 타입의 userId 필드로 유저를 찾는 쿼리 메서드
    Optional<User> findByUserId(String userId);
}
