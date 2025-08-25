package com.github.hstardawn.bighomework.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @TableName post
 */
@Data
public class Post {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String content;

    private Integer userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer deletedAt;
}