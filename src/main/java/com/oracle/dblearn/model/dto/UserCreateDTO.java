package com.oracle.dblearn.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

}
