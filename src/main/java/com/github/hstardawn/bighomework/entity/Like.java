package com.github.hstardawn.bighomework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName like
 */
@TableName(value = "`like`")
@Data
public class Like {
    private Integer id;

    private Integer userId;

    private Integer postId;

    private Boolean status;
}