package com.github.hstardawn.bighomework.service;

import com.github.hstardawn.bighomework.dto.response.GetUncheckPost;

import java.util.List;

public interface AdminService {
    List<GetUncheckPost> getUncheckPosts(Integer userId);

    void checkReport(Integer userId, Integer reportId, Integer approval);
}
