package com.prodapt.mobileplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByMobile(String mobile);
    Optional<UserAuth> findByEmail(String email);
}
