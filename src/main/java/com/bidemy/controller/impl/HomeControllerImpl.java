package com.bidemy.controller.impl;

import com.bidemy.controller.IHomeController;
import com.bidemy.jwt.AuthRequest;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerImpl implements IHomeController {

    @GetMapping("/home")
    @Override
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("authRequest",new AuthRequest());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerRequest",new RegisterRequest());
        return "register";
    }



}
