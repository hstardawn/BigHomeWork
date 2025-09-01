package com.github.hstardawn.bighomework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CheckReportRequest {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    @JsonProperty("report_id")
    private Integer reportId;

    @NotNull
    @Max(2)
    @Min(1)
    private Integer approval;
}
