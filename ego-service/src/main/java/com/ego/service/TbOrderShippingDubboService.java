package com.ego.service;

import com.ego.pojo.TbOrderShipping;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:25
 */
public interface TbOrderShippingDubboService {

    // 新增订单发货信息
    int insertTbOrderShipping(TbOrderShipping tbOrderShipping);
}
