package com.bidemy.controller;

import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;

public interface IRestAuthController {
    public UserDTO register(RegisterRequest request);

    public AuthResponse authenticate(AuthRequest request);

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
