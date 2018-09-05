package com.ego.order.service;

import com.ego.order.pojo.MyOrder;

import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:11
 */
public interface TbOrderService {
    // 插入订单信息
    Map<String,Object> insertOrder(MyOrder myOrder);
}
