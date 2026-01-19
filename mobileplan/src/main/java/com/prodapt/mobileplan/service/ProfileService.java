package com.prodapt.mobileplan.service;

import com.prodapt.mobileplan.dto.request.ProfileUpdateRequest;
import com.prodapt.mobileplan.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request);
}
