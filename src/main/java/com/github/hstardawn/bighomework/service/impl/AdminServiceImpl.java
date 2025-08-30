package com.github.hstardawn.bighomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.hstardawn.bighomework.constant.ExceptionEnum;
import com.github.hstardawn.bighomework.dto.response.GetUncheckPost;
import com.github.hstardawn.bighomework.entity.Post;
import com.github.hstardawn.bighomework.entity.Report;
import com.github.hstardawn.bighomework.entity.User;
import com.github.hstardawn.bighomework.exception.ApiException;
import com.github.hstardawn.bighomework.mapper.PostMapper;
import com.github.hstardawn.bighomework.mapper.ReportMapper;
import com.github.hstardawn.bighomework.mapper.UserMapper;
import com.github.hstardawn.bighomework.service.AdminService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Resource

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final ReportMapper reportMapper;

    @Override
    public List<GetUncheckPost> getUncheckPosts(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }
        if (user.getUserType() != 2) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }

        LambdaQueryWrapper<Report> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Report::getStatus, 0);
        List<Report> reports = reportMapper.selectList(queryWrapper);


        return reports.stream().map(
                report -> {
                    Post post = postMapper.selectById(report.getReporterId());

                    return GetUncheckPost.builder()
                            .id(report.getId())
                            .username(userMapper.selectById(post.getUserId()).getUsername())
                            .postId(post.getId())
                            .content(post.getContent())
                            .reason(report.getReason())
                            .build();
                }).toList();
    }

    @Override
    public void checkReport(Integer userId, Integer reportId, Integer approval) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }
        if (user.getUserType() != 2) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }

        Report report = reportMapper.selectById(reportId);
        if (report == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        if (report.getStatus() != 0) {
            throw new ApiException(ExceptionEnum.REPORT_ALREADY_CHECKED);
        }

        if (approval == 1) {
            report.setStatus(2);
            reportMapper.updateById(report);
        } else if (approval == 2) {
            report.setStatus(1);
            reportMapper.updateById(report);
            postMapper.deleteById(report.getReporterId());
        } else {
            throw new ApiException(ExceptionEnum.INVALID_PARAMETER);
        }
    }
}
