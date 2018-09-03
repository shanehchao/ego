package com.ego.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
    //新增商品描述
    int insertTbItemDesc(TbItemDesc tbItemDesc);

    // 根据商品id查询商品描述
    TbItemDesc selectByTbItemId(long tbItemId);
}
