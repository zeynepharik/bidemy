package com.bidemy.controller.impl;

import com.bidemy.controller.IRestAuthController;
import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.AuthResponse;
import com.bidemy.jwt.RefreshTokenRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.mapper.UserMapper;
import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import com.bidemy.service.impl.AuthServiceImpl;
import com.bidemy.service.impl.RefreshTokenServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestAuthControllerImpl implements IRestAuthController {

    private final AuthServiceImpl authService;

    private final RefreshTokenServiceImpl refreshTokenService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ICategoryService categoryService;

    private final ICourseService courseService;


    @PostMapping("/register")
    @Override
    public String register(@Valid  RegisterRequest request, HttpSession session) {
        UserDTO userDTO = authService.register(request);
        session.setAttribute("currentUser",userDTO);

        return "home";
    }

    @PostMapping("/authenticate")
    @Override
    public String authenticate(@Valid AuthRequest request, Model model,HttpSession session) {
        AuthResponse authResponse = authService.authenticate(request);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));

        session.setAttribute("currentUser", userMapper.toDTO(user));
        List<CategoryDTO> categories = categoryService.getAll();
        model.addAttribute("categories",categories);
        System.out.println(categories.size());
        model.addAttribute("courses",courseService.getAll());
        return "home";
    }

    @PostMapping("/refreshToken")
    @Override
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request);
    }
}
