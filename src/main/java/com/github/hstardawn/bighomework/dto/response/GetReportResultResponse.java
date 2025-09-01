package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class GetReportResultResponse {
    @NotNull
    @JsonProperty("post_id")
    private Integer postId;

    @NotBlank
    private String content;

    @NotBlank
    private String reason;

    @NotNull
    private Integer status;
}
