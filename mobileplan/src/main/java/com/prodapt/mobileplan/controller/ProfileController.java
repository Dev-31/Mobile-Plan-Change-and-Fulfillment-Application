package com.prodapt.mobileplan.controller;

import org.springframework.web.bind.annotation.*;

import com.prodapt.mobileplan.dto.request.ProfileUpdateRequest;
import com.prodapt.mobileplan.dto.response.ProfileResponse;
import com.prodapt.mobileplan.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ProfileResponse getProfile(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }

    @PutMapping("/{userId}")
    public ProfileResponse updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest request) {
        return profileService.updateProfile(userId, request);
    }
}
