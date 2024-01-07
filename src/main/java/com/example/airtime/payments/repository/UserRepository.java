package com.example.airtime.payments.repository;


import com.example.airtime.payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean  existsByEmail(String email);
}

