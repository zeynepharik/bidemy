package com.bidemy.controller;

import com.bidemy.model.dto.UserDTO;

import java.util.List;

public interface IUserController {
    UserDTO create(UserDTO dto);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    UserDTO update(Long id,UserDTO dto);
    void delete(Long id);
}
