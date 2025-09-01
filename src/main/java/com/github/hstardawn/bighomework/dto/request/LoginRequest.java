package com.github.hstardawn.bighomework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginRequest {
    @Size(max = 20)
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
