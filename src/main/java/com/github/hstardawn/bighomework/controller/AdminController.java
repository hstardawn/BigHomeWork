package com.github.hstardawn.bighomework.controller;

import com.github.hstardawn.bighomework.dto.request.CheckReportRequest;
import com.github.hstardawn.bighomework.dto.response.GetUncheckPost;
import com.github.hstardawn.bighomework.dto.response.GetUncheckPostList;
import com.github.hstardawn.bighomework.result.AjaxResult;
import com.github.hstardawn.bighomework.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/report")
    public AjaxResult<GetUncheckPostList> getReport(@Valid @RequestParam("user_id") Integer userId) {
        List<GetUncheckPost> lists = adminService.getUncheckPosts(userId);
        GetUncheckPostList result = GetUncheckPostList.builder()
                .uncheckPostList(lists)
                .build();

        return AjaxResult.success(result);
    }

    @PostMapping("/report")
    public AjaxResult<Void> checkReport(@Valid @RequestBody CheckReportRequest request) {
        adminService.checkReport(request.getUserId(), request.getReportId(), request.getApproval());
        return AjaxResult.success();
    }
}
