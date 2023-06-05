package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.error.SportException;

/**
 * Create by LWC on 2023/4/2 18:36
 */
public interface LikeService {
    void create(String telephone, Integer postId) throws SportException;
    void delete(String telephone, Integer postId) throws SportException;
    int getCount(Integer postId);




//    <select id="selectCntByPostId" parameterType="java.lang.Integer" resultType="java.lang.Integer">
//    select count(*)
//    from like
//    where post_id = #{postId,jdbcType=INTEGER}
//  </select>
}
