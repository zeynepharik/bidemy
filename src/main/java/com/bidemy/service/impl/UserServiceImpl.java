package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.jwt.RegisterRequest;
import com.bidemy.mapper.UserMapper;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO create(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users){
            userDTOS.add(userMapper.toDTO(user));
        }
        return userDTOS;
    }

    @Override
    public UserDTO update(Long id, RegisterRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
