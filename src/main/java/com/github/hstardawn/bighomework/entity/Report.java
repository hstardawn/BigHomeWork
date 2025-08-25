package com.github.hstardawn.bighomework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName report
 */
@TableName(value ="report")
@Data
public class Report {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer reporterId;

    private Integer status;
}