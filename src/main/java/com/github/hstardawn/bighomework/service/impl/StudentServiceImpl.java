package com.github.hstardawn.bighomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.hstardawn.bighomework.constant.ExceptionEnum;
import com.github.hstardawn.bighomework.dto.response.GetReportResultResponse;
import com.github.hstardawn.bighomework.dto.response.PostElementsResponse;
import com.github.hstardawn.bighomework.entity.Post;
import com.github.hstardawn.bighomework.entity.Report;
import com.github.hstardawn.bighomework.entity.User;
import com.github.hstardawn.bighomework.exception.ApiException;
import com.github.hstardawn.bighomework.mapper.PostMapper;
import com.github.hstardawn.bighomework.mapper.ReportMapper;
import com.github.hstardawn.bighomework.mapper.UserMapper;
import com.github.hstardawn.bighomework.service.StudentService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.github.hstardawn.bighomework.constant.RedisKeyConstant.POST_LIKE_COUNT_KEY;
import static com.github.hstardawn.bighomework.constant.RedisKeyConstant.POST_LIKE_SET_KEY;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final ReportMapper reportMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publishPost(String content, Integer userId) {
        User user = userMapper.selectById(userId);

        if (user == null) {
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
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
            throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
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
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }

        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
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
            throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
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
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }

        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
        }

        String likeSetKey = POST_LIKE_SET_KEY + postId;
        String likeCountKey = POST_LIKE_COUNT_KEY + postId;

        Boolean isMember = redisTemplate.opsForSet().isMember(likeSetKey, userId);

        if (Boolean.TRUE.equals(isMember)) {
            // 已点赞 -> 取消
            redisTemplate.opsForSet().remove(likeSetKey, userId);

            // 避免负数
            Long count = redisTemplate.opsForValue().decrement(likeCountKey);
            if (count != null && count < 0) {
                redisTemplate.opsForValue().set(likeCountKey, "0");
            }

            return false;
        } else {
            // 未点赞 -> 点赞
            redisTemplate.opsForSet().add(likeSetKey, userId);

            // 初始化点赞数为 0 再自增
            if (Boolean.FALSE.equals(redisTemplate.hasKey(likeCountKey))) {
                redisTemplate.opsForValue().set(likeCountKey, "0");
            }
            redisTemplate.opsForValue().increment(likeCountKey);

            return true;
        }
    }

    public int getLikeCount(Integer postId, Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
        }

        String key = POST_LIKE_COUNT_KEY + postId;
        Object count = redisTemplate.opsForValue().get(key);
        if (count != null) {
            return (int) count;
        }
        // Redis 没有，就查 DB 并写入 Redis
        if (post.getLikes() != null) {
            redisTemplate.opsForValue().set(key, post.getLikes());
            return post.getLikes();
        }
        return 0;
    }

    @Override
    public List<GetReportResultResponse> getReportResults(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException(ExceptionEnum.USER_NOT_FOUND);
        }

        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getUserId, userId);
        List<Report> reports = reportMapper.selectList(wrapper);
        if (reports.isEmpty()) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }

        return reports.stream().map(report -> {
            Post post = postMapper.selectById(report.getReporterId());
            if (post == null) {
                throw new ApiException(ExceptionEnum.POST_NOT_FOUND);
            }
            return GetReportResultResponse.builder()
                    .postId(report.getReporterId())
                    .reason(report.getReason())
                    .content(post.getContent())
                    .status(report.getStatus())
                    .build();
        }).toList();
    }

    @Scheduled(fixedRate = 300000) // 每5分钟执行一次
    public void syncLikesToDB() {
        Set<String> keys = redisTemplate.keys(POST_LIKE_COUNT_KEY + "*");
        if (keys.isEmpty()) {
            return; // 没有数据就直接返回
        }

        for (String key : keys) {
            String postIdStr = key.replace(POST_LIKE_COUNT_KEY, "");
            Integer postId = Integer.valueOf(postIdStr);

            Object value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                Integer likeCount = Integer.parseInt(value.toString());
                postMapper.updateLikeCount(postId, likeCount);
            }
        }
    }
}
