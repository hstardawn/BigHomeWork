package com.github.hstardawn.bighomework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetReportResultListResponse {
    @JsonProperty("report_list")
    private List<GetReportResultResponse> reportResultList;
}
