package com.ego.item.service;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午4:54
 */
public interface TbItemParamItemService {

    // 根据商品id查询规则参数内容
    String selectByTbItemId(long tbItemId);
}
