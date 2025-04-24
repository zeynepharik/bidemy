package com.bidemy.service;

import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {
    public AuthResponse refreshToken(RefreshTokenRequest request);
}
