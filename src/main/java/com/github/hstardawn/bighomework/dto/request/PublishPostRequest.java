package com.github.hstardawn.bighomework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublishPostRequest {
    @NotBlank
    @Size(max = 500)
    private String content;

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
