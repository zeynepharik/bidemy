package com.bidemy.service.impl;

import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.JwtService;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.model.entity.RefreshToken;
import com.bidemy.repository.RefreshTokenRepository;
import com.bidemy.service.IAuthService;
import com.bidemy.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.PackageElement;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthServiceImpl authService;

    public Boolean isRefreshTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optional = tokenRepository.findByRefreshToken(request.getRefreshToken());
        if (optional.isEmpty()) {
            System.out.println("Refresh Token geçersizdir " + request.getRefreshToken());
        }

        RefreshToken refreshToken = optional.get();

        if (!isRefreshTokenExpired(refreshToken.getExpireDate())) {
            System.out.println("Refresh tokenın süresi bitmiştir." + request.getRefreshToken());
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken savedRefreshToken = tokenRepository.save(authService.createRefreshToken(refreshToken.getUser()));

        return new AuthResponse(savedRefreshToken.getRefreshToken(), accessToken);
    }
}












