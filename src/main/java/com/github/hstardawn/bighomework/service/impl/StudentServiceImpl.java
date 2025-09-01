package com.github.hstardawn.bighomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.hstardawn.bighomework.constant.ExceptionEnum;
import com.github.hstardawn.bighomework.dto.response.GetReportResultResponse;
import com.github.hstardawn.bighomework.dto.response.PostElementsResponse;
import com.github.hstardawn.bighomework.entity.Like;
import com.github.hstardawn.bighomework.entity.Post;
import com.github.hstardawn.bighomework.entity.Report;
import com.github.hstardawn.bighomework.entity.User;
import com.github.hstardawn.bighomework.exception.ApiException;
import com.github.hstardawn.bighomework.mapper.LikeMapper;
import com.github.hstardawn.bighomework.mapper.PostMapper;
import com.github.hstardawn.bighomework.mapper.ReportMapper;
import com.github.hstardawn.bighomework.mapper.UserMapper;
import com.github.hstardawn.bighomework.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final ReportMapper reportMapper;
    private final LikeMapper likeMapper;

    @Override
    public void publishPost(String content, Integer userId) {
        User user = userMapper.selectById(userId);

        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        Post post = Post.builder()
                .content(content)
                .userId(userId)
                .build();


        postMapper.insert(post);
    }

    @Override
    public List<PostElementsResponse> getPosts() {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Post::getCreatedAt);

        List<Post> posts = postMapper.selectList(wrapper);

        // 转换成 DTO
        return posts.stream().map(
                post -> PostElementsResponse.builder()
                        .id(post.getId())
                        .userId(post.getUserId())
                        .createdAt(post.getCreatedAt())
                        .content(post.getContent())
                        .likes(getLikeCount(post.getId(), post.getUserId()))
                        .build()
        ).toList();
    }

    @Override
    public void deletePost(Integer userId, Integer postId) {
        Post post = postMapper.selectById(postId);

        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        if (!post.getUserId().equals(userId)) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }

        postMapper.deleteById(postId);
    }

    @Override
    public void reportPost(Integer userId, Integer postId, String reason) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        Report reportClass = Report.builder()
                .reporterId(postId)
                .reason(reason)
                .userId(userId)
                .build();

        reportMapper.insert(reportClass);
    }

    @Override
    public void updatePost(Integer userId, Integer postId, String content) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        if (!post.getUserId().equals(userId)) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }

        post.setContent(content);
        postMapper.updateById(post);
    }

    @Override
    public boolean toggleLike(Integer postId, Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        likeMapper.toggleLike(userId, postId);

        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getPostId, postId).eq(Like::getUserId, userId);
        return likeMapper.selectOne(wrapper).getStatus();
    }

    @Override
    public int getLikeCount(Integer postId, Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getPostId, postId).eq(Like::getUserId, userId).eq(Like::getStatus, true);
        return Math.toIntExact(likeMapper.selectCount(wrapper));
    }

    @Override
    public List<GetReportResultResponse> getReportResults(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getUserId, userId);
        List<Report> reports = reportMapper.selectList(wrapper);
        if (reports.isEmpty()) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        return reports.stream().map(report -> {
            Post post = postMapper.selectById(report.getReporterId());
            return GetReportResultResponse.builder()
                    .postId(report.getReporterId())
                    .reason(report.getReason())
                    .content(post == null ? "" : post.getContent())
                    .status(report.getStatus())
                    .build();
        }).toList();
    }
}
