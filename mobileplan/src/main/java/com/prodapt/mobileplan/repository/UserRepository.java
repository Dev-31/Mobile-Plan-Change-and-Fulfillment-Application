package com.prodapt.mobileplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String mobileNumber);
}
