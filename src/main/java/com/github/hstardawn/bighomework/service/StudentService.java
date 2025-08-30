package com.github.hstardawn.bighomework.service;

import com.github.hstardawn.bighomework.dto.response.GetReportResultResponse;
import com.github.hstardawn.bighomework.dto.response.PostElementsResponse;

import java.util.List;

public interface StudentService {
    // 发布帖子
    void publishPost(String content, Integer userId);

    // 获取所有帖子
    List<PostElementsResponse> getPosts();

    // 删除帖子
    void deletePost(Integer userId, Integer postId);

    // 举报帖子
    void reportPost(Integer userId, Integer postId, String reason);

    // 更新帖子
    void updatePost(Integer userId, Integer postId, String content);

    // 点赞or取消
    boolean toggleLike(Integer postId, Integer userId);

    int getLikeCount(Integer postId, Integer userId);

    List<GetReportResultResponse> getReportResults(Integer userId);
}
