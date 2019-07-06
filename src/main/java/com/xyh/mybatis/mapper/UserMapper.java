package com.xyh.mybatis.mapper;

import com.xyh.mybatis.entity.User;

/**
 * @author xuyh
 * @date 2019/7/2
 */
public interface UserMapper {
    User getOne(Integer id);
}
