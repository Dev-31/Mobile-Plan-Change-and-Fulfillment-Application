package com.prodapt.mobileplan.service;

public interface JwtService {

    String generateToken(String mobile);

    String extractMobile(String token);

    boolean validateToken(String token);
}
