package com.ego.service.impl;

import com.ego.mapper.TbOrderMapper;
import com.ego.pojo.TbOrder;
import com.ego.service.TbOrderDubboService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:01
 */
public class TbOrderDubboServiceImpl implements TbOrderDubboService {

    @Resource
    private TbOrderMapper tbOrderMapper;

    // 新增订单
    @Override
    public int insertTbOrder(TbOrder tbOrder) {
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        return tbOrderMapper.insertSelective(tbOrder);
    }
}
