package com.bidemy.mapper;

import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
