package com.example.spring.CafeManagerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.CafeManagerApplication.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
