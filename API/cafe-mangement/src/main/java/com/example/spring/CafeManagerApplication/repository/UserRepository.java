package com.example.spring.CafeManagerApplication.repository;

import com.example.spring.CafeManagerApplication.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(value = "SELECT u FROM UserEntity u ")
    List<UserEntity> findAllUsers(Sort sort);
}


