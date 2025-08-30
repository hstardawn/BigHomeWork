package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetUncheckPost {
    @JsonProperty("report_id")
    private Integer id;

    private String username;

    @JsonProperty("post_id")
    private Integer postId;

    private String content;

    private String reason;
}
