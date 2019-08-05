package com.xyh.service.impl;

import com.xyh.bean.UserAddress;
import com.xyh.service.IOrderService;
import com.xyh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/30
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IUserService userService;
    @Override
    public void initOrder(Integer userId) {
        List<UserAddress> userAddresses = userService.listUserAddress(userId);
        System.out.println(userAddresses.toString());
    }
}
