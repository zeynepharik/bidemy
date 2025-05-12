package com.bidemy.service.impl;

import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.JwtService;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.model.entity.RefreshToken;
import com.bidemy.repository.RefreshTokenRepository;
import com.bidemy.service.IRefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final RefreshTokenRepository tokenRepository;

    private final JwtService jwtService;

    private final AuthServiceImpl authService;

    public Boolean isRefreshTokenExpired(Date expiredDate) {
        return (new Date()).after(expiredDate);
    }


    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optional = tokenRepository.findByRefreshToken(request.getRefreshToken());
        if (!optional.isPresent()) {
            System.out.println("Refresh Token geçersizdir " + request.getRefreshToken());
            return new AuthResponse(null, null);
        } else {
            RefreshToken refreshToken = optional.get();
            if (isRefreshTokenExpired(refreshToken.getExpireDate())) {
                System.out.println("Refresh tokenın süresi bitmiştir." + request.getRefreshToken());
                return new AuthResponse(null, null);
            } else {
                String accessToken = jwtService.generateToken(refreshToken.getUser());
                RefreshToken savedRefreshToken = tokenRepository.save(authService.createRefreshToken(refreshToken.getUser()));
                return new AuthResponse(savedRefreshToken.getRefreshToken(), accessToken);
            }
        }
    }
}












