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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;


    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        return refreshToken;
    }


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
                authenticationProvider.authenticate(authentication);
            Optional<User> user = userRepository.findByEmail(authRequest.getEmail());
            String accessToken = jwtService.generateToken(user.get());

            RefreshToken refreshToken = createRefreshToken(user.get());
            refreshTokenRepository.save(refreshToken);

            return new AuthResponse(refreshToken.getRefreshToken(), accessToken);
        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı ");
        }
        return null;
    }


    @Override
    public UserDTO register(RegisterRequest request) {
        UserDTO dto = new UserDTO();
        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dto);
        return dto;
    }
}
