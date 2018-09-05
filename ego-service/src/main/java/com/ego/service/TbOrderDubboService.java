package com.ego.service;

import com.ego.pojo.TbOrder;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午4:59
 */
public interface TbOrderDubboService {

    // 新增订单
    int insertTbOrder(TbOrder tbOrder);
}
