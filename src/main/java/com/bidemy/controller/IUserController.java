package com.bidemy.controller;

import com.bidemy.jwt.RegisterRequest;
import com.bidemy.model.dto.UserDTO;

import java.util.List;

public interface IUserController {
    UserDTO create(UserDTO dto);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    UserDTO update(Long id, RegisterRequest request);
    void delete(Long id);
}
