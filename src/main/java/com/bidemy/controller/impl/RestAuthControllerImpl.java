package com.bidemy.controller.impl;

import com.bidemy.controller.IRestAuthController;
import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.service.impl.AuthServiceImpl;
import com.bidemy.service.impl.RefreshTokenServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @PostMapping("/register")
    @Override
    public UserDTO register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    @Override
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refreshToken")
    @Override
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request);
    }
}
