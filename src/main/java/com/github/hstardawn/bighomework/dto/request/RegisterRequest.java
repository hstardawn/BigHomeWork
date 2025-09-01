package com.github.hstardawn.bighomework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[0-9]+$")
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @NotNull
    private int userType;
}
