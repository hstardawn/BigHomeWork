package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostElementsResponse {
    private Integer id;

    private String content;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("time")
    private LocalDateTime createdAt;

    private Integer likes;
}
