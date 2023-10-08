package com.example.spring.assaignmentsubmissionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.assaignmentsubmissionapp.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
