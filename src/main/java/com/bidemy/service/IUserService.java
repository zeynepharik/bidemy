package com.bidemy.service;

import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import com.bidemy.model.request.UserRequest;

import java.util.List;

public interface IUserService {
    UserDTO create(UserRequest userRequest);

    UserDTO getById(Long id);

    List<UserDTO> getAll();

    UserDTO update(Long id, UserRequest request);

    UserDTO getUserByEmail(String email);

    void delete(Long id);
}
