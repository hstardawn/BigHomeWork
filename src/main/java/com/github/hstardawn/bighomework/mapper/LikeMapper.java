package com.github.hstardawn.bighomework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.hstardawn.bighomework.entity.Like;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author SugarMGP
 * @description 针对表【like】的数据库操作Mapper
 * @createDate 2025-09-01 19:37:24
 * @Entity com.github.hstardawn.bighomework.entity.Like
 */
public interface LikeMapper extends BaseMapper<Like> {
    @Insert("""
            INSERT INTO `like` (user_id, post_id, status)
            VALUES (#{userId}, #{postId}, true)
            ON DUPLICATE KEY UPDATE
              status = NOT status
            """)
    void toggleLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
}




