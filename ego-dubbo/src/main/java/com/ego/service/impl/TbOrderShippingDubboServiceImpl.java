package com.ego.service.impl;

import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrderShipping;
import com.ego.service.TbOrderShippingDubboService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:28
 */
public class TbOrderShippingDubboServiceImpl implements TbOrderShippingDubboService {

    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    // 新增订单发货信息
    @Override
    public int insertTbOrderShipping(TbOrderShipping tbOrderShipping) {
        Date date = new Date();
        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);
        return tbOrderShippingMapper.insertSelective(tbOrderShipping);
    }
}
