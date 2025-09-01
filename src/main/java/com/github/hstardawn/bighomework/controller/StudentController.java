package com.github.hstardawn.bighomework.controller;

import com.github.hstardawn.bighomework.dto.request.*;
import com.github.hstardawn.bighomework.dto.response.GetReportResultListResponse;
import com.github.hstardawn.bighomework.dto.response.GetReportResultResponse;
import com.github.hstardawn.bighomework.dto.response.PostElementsResponse;
import com.github.hstardawn.bighomework.dto.response.PostListResponse;
import com.github.hstardawn.bighomework.result.AjaxResult;
import com.github.hstardawn.bighomework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/post")
    public AjaxResult<Void> publishPost(@Valid @RequestBody PublishPostRequest request) {
        studentService.publishPost(request.getContent(), request.getUserId());
        return AjaxResult.success();
    }

    @GetMapping("/post")
    public AjaxResult<PostListResponse> getPosts() {
        List<PostElementsResponse> data = studentService.getPosts();

        PostListResponse response = PostListResponse.builder()
                .postList(data)
                .build();

        return AjaxResult.success(response);
    }

    @DeleteMapping("/post")
    public AjaxResult<Void> deletePost(@RequestParam("user_id") Integer userId, @RequestParam("post_id") Integer postId) {
        studentService.deletePost(userId, postId);
        return AjaxResult.success();
    }

    @PostMapping("/report-post")
    public AjaxResult<Void> reportPost(@Valid @RequestBody ReportPostRequest request) {
        studentService.reportPost(request.getUserId(), request.getPostId(), request.getReason());
        return AjaxResult.success();
    }

    @PutMapping("/post")
    public AjaxResult<Void> updatePost(@Valid @RequestBody UpdatePostRequest request) {
        studentService.updatePost(request.getUserId(), request.getPostId(), request.getContent());
        return AjaxResult.success();
    }

    @PostMapping("/likes")
    public AjaxResult<Map<String, Object>> toggleLike(@Valid @RequestBody ToggleLikeRequest request) {
        boolean liked = studentService.toggleLike(request.getPost_id(), request.getUser_id());
        int likeCount = studentService.getLikeCount(request.getPost_id(), request.getUser_id());

        Map<String, Object> result = new HashMap<>();
        result.put("liked", liked);
        result.put("likeCount", likeCount);

        return AjaxResult.success(result);
    }

    /**
     * 查询点赞数
     */
    @GetMapping("/likes")
    public AjaxResult<Integer> getLikeCount(@Valid GetLikesRequest request) {
        return AjaxResult.success(studentService.getLikeCount(request.getPost_id(), request.getUser_id()));
    }

    @GetMapping("report-post")
    public AjaxResult<GetReportResultListResponse> getReportResult(@Valid @RequestParam("user_id") Integer userId) {
        List<GetReportResultResponse> reports = studentService.getReportResults(userId);

        GetReportResultListResponse result = GetReportResultListResponse.builder()
                .reportResultList(reports)
                .build();

        return AjaxResult.success(result);
    }
}

