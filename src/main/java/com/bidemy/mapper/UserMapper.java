package com.bidemy.mapper;

import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.entity.User;
import com.bidemy.model.entity.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest request);

    UserDTO toDTO(User entity);
}
