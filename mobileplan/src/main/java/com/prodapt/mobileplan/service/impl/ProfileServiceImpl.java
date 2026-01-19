package com.prodapt.mobileplan.service.impl;

import org.springframework.stereotype.Service;

import com.prodapt.mobileplan.dto.request.ProfileUpdateRequest;
import com.prodapt.mobileplan.dto.response.ProfileResponse;
import com.prodapt.mobileplan.entity.User;
import com.prodapt.mobileplan.entity.UserProfile;
import com.prodapt.mobileplan.repository.UserProfileRepository;
import com.prodapt.mobileplan.repository.UserRepository;
import com.prodapt.mobileplan.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public ProfileServiceImpl(UserProfileRepository userProfileRepository,
                              UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileResponse getProfile(Long userId) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultProfile(userId));

        return map(profile);
    }

    @Override
    public ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultProfile(userId));

        if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
            profile.setFullName(request.getFullName().trim());
        }

        if (request.getAddress() != null) {
            profile.setAddress(request.getAddress());
        }

        profile.setSmsEnabled(request.isSmsEnabled());
        profile.setEmailEnabled(request.isEmailEnabled());
        profile.setPushEnabled(request.isPushEnabled());

        return map(userProfileRepository.save(profile));
    }


    // 🔹 AUTO-CREATE PROFILE
    private UserProfile createDefaultProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setEmail(user.getMobileNumber() + "@mobileplan.com");
        profile.setSmsEnabled(true);
        profile.setEmailEnabled(true);
        profile.setPushEnabled(true);

        return userProfileRepository.save(profile);
    }

    private ProfileResponse map(UserProfile profile) {
        ProfileResponse res = new ProfileResponse();
        res.setUserId(profile.getUser().getId());
        res.setFullName(profile.getFullName());
        res.setEmail(profile.getEmail());
        res.setAddress(profile.getAddress());
        res.setSmsEnabled(profile.isSmsEnabled());
        res.setEmailEnabled(profile.isEmailEnabled());
        res.setPushEnabled(profile.isPushEnabled());
        return res;
    }
}
