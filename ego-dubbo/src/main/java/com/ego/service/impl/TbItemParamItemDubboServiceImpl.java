package com.ego.service.impl;

import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemDubboService;

import javax.annotation.Resource;

/**
 * 规格参数值
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/29 下午6:34
 */
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;
    // 新增规格参数值
    @Override
    public int insertTbItemParamItem(TbItemParamItem tbItemParamItem) {
        return tbItemParamItemMapper.insertSelective(tbItemParamItem);
    }
}
