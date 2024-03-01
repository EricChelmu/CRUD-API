package com.stns.crudapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "username shouldn't be null")
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String roles;
}
