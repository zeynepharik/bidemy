package com.bidemy.service.impl;

import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.JwtService;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.RefreshToken;
import com.bidemy.model.entity.User;
import com.bidemy.repository.RefreshTokenRepository;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        return refreshToken;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            authenticationProvider.authenticate(authentication);

            Optional<User> user = userRepository.findByEmail(authRequest.getEmail());
            String accessToken = jwtService.generateToken(user.get());
            RefreshToken refreshToken = createRefreshToken(user.get());
            refreshTokenRepository.save(refreshToken);
            return new AuthResponse(refreshToken.getRefreshToken(),accessToken );
        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı ");
            return null;
        }
    }

    public UserDTO register(RegisterRequest request) {
        UserDTO dto = new UserDTO();
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dto);
        return dto;
    }

}

