package com.ego.service.impl;

import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescDubboService;

import javax.annotation.Resource;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    //新增商品描述
    @Override
    public int insertTbItemDesc(TbItemDesc tbItemDesc) {
        return tbItemDescMapper.insertSelective(tbItemDesc);
    }

    // 根据商品id查询商品描述
    @Override
    public TbItemDesc selectByTbItemId(long tbItemId) {
        return tbItemDescMapper.selectByPrimaryKey(tbItemId);
    }
}
