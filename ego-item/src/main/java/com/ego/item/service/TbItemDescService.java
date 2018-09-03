package com.ego.item.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午4:34
 */
public interface TbItemDescService {

    // 根据商品id查询商品描述
    TbItemDesc selectByTbItemId(long tbItemId);
}
