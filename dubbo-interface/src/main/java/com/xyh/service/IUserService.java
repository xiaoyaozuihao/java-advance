package com.xyh.service;

import com.xyh.bean.UserAddress;

import java.util.List;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/30
 */
public interface IUserService {
    List<UserAddress> listUserAddress(Integer userId);
}
