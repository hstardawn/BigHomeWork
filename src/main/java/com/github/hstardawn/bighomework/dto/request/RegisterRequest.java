package com.github.hstardawn.bighomework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

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
    @JsonProperty("user_type")
    @Min(1)
    @Max(2)
    private int userType;
}
