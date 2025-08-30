package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetUncheckPostList {
    @JsonProperty("report_list")
    List<GetUncheckPost> uncheckPostList;
}
