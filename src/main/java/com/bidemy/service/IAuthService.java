package com.bidemy.service;

import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;

public interface IAuthService {
    UserDTO register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest authRequest);
}
