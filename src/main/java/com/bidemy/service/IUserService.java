package com.bidemy.service;

import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;

import java.util.List;

public interface IUserService {
    UserDTO create(UserDTO dto);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    UserDTO update(Long id,UserDTO dto);
    void delete(Long id);
}
