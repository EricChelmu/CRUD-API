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
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered")
    private String mobile;
    private String gender;
    @Min(18)
    @Max(120)
    private int age;
    @NotBlank
    private String nationality;
}
