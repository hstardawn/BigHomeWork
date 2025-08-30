package com.github.hstardawn.bighomework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.hstardawn.bighomework.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author apple
 * @description 针对表【post】的数据库操作Mapper
 * @createDate 2025-08-23 21:02:02
 * @Entity com.github.hstardawn.bighomework.entity.Post
 */
public interface PostMapper extends BaseMapper<Post> {
    @Update("UPDATE post SET likes = #{likeCount} WHERE id = #{postId}")
    void updateLikeCount(@Param("postId") Integer postId, @Param("likeCount") Integer likeCount);
}




