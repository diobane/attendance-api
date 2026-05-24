package com.attendance_api.domain.repository;

import com.attendance_api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndActiveTrue(String username);

    Optional<User> findByUsername(String username);
}