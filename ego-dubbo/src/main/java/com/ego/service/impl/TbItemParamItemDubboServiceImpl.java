package com.ego.service.impl;

import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import com.ego.service.TbItemParamItemDubboService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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

    // 根据商品id查询规则参数内容
    @Override
    public TbItemParamItem selectByTbItemId(long tbItemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(tbItemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(tbItemParamItems)) {
            return null;
        }
        return tbItemParamItems.get(0);
    }
}
