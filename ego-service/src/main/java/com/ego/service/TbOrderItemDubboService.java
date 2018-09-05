package com.ego.service;

import com.ego.pojo.TbOrderItem;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:25
 */
public interface TbOrderItemDubboService {

    // 新增订单商品信息
    int insertTbOrderItem(TbOrderItem tbOrderItem);
}
