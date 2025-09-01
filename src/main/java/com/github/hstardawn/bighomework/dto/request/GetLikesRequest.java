package com.github.hstardawn.bighomework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GetLikesRequest {
    @NotNull
    private Integer user_id;

    @NotNull
    private Integer post_id;
}
