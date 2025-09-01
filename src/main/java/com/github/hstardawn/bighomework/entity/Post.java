package com.github.hstardawn.bighomework.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @TableName post
 */
@TableName(value = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private Integer userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deletedAt;
}