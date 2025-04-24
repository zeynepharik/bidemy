package com.bidemy.controller;

import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface IRestAuthController {
    public String register(RegisterRequest request, HttpSession session);

    public String authenticate(AuthRequest request, Model model, HttpSession session);

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
