package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.entity.User;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String mobileNumber) {

        return userRepository.findByMobileNumber(mobileNumber)
                .orElseGet(() -> {
                    User user = new User();
                    user.setMobileNumber(mobileNumber);
                    return userRepository.save(user);
                });
    }
}
