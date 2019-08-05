package com.xyh.service.impl;

import com.xyh.bean.UserAddress;
import com.xyh.service.IUserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/30
 */
public class UserServiceImpl implements IUserService {
    @Override
    public List<UserAddress> listUserAddress(Integer userId) {
        UserAddress userAddress = new UserAddress(1, "杭州市西湖区文二路", 10001, "张三", "18828282813", "Y");
        UserAddress userAddress1 = new UserAddress(2, "杭州市西湖区西溪壹号", 10002, "李四", "18834222813", "Y");
        return Arrays.asList(userAddress,userAddress1);
    }
}
