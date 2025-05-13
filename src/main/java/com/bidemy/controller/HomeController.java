package com.bidemy.controller;

import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ICourseService courseService;
    private final ICategoryService categoryService;

    @GetMapping({"/home"})
    public String home(Model model, HttpSession session) {
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    @GetMapping({"/login"})
    public String login(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        return "login";
    }

    @GetMapping({"/register"})
    public String register(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }



}
