package com.bidemy.controller.impl;

import com.bidemy.controller.IUserController;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerImpl implements IUserController {

    private final IUserService userService;

    @PostMapping
    @Override
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    @Override
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    @Override
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    @Override
    public UserDTO update(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
