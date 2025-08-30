package com.github.hstardawn.bighomework.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetLikesRequest {
    @NotNull
    private Integer user_id;

    @NotNull
    private Integer post_id;
}
