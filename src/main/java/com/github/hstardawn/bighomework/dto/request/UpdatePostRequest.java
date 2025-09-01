package com.github.hstardawn.bighomework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdatePostRequest {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    @JsonProperty("post_id")
    private Integer postId;

    @NotNull
    private String content;
}
