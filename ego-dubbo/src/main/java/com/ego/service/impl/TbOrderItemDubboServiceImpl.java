package com.ego.service.impl;

import com.ego.mapper.TbOrderItemMapper;
import com.ego.pojo.TbOrderItem;
import com.ego.service.TbOrderItemDubboService;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:31
 */
public class TbOrderItemDubboServiceImpl implements TbOrderItemDubboService {

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    // 新增订单商品信息
    @Override
    public int insertTbOrderItem(TbOrderItem tbOrderItem) {
        return tbOrderItemMapper.insertSelective(tbOrderItem);
    }
}
