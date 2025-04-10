package com.bidemy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull(message = "User ID cannot be null")
    private Long id;

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email is required")
    private String email;
}
