package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostListResponse {
    @JsonProperty("post_list")
    private List<PostElementsResponse> postList;
}
