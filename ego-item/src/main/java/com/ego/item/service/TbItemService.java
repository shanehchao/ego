package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午3:38
 */
public interface TbItemService {
    // 根据商品id查询商品详情
    TbItemChild selectByTbItemId(long id);
}
