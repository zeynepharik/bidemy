package com.bidemy.controller;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.jwt.*;
import com.bidemy.mapper.UserMapper;
import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import com.bidemy.model.response.CategoryResponse;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import com.bidemy.service.impl.AuthServiceImpl;
import com.bidemy.service.impl.RefreshTokenServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestAuthController {
    private final AuthServiceImpl authService;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ICategoryService categoryService;
    private final ICourseService courseService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping({"/register"})
    public String register(@RequestBody @Valid RegisterRequest request, HttpSession session) {
        UserDTO userDTO = authService.register(request);
        session.setAttribute("currentUser", userDTO);
        return "home";
    }

    @PostMapping({"/authenticate"})
    public String authenticate(@ModelAttribute @Valid AuthRequest request, Model model, HttpSession session, HttpServletResponse response) {
        authService.authenticate(request);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtService.generateToken(user);
        System.out.println("JWT Token: " + jwtToken);
        ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken).httpOnly(true).path("/").maxAge(86400L).build();
        response.setHeader("Set-Cookie", cookie.toString());
        session.setAttribute("currentUser", userMapper.toDTO(user));
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("courses", courseService.getAll());
        return "redirect:/home";
    }

    @GetMapping({"/logout"})
    public String logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", "");
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
        return "redirect:/login";
    }

    @PostMapping({"/refreshToken"})
    public AuthResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request);
    }
}
