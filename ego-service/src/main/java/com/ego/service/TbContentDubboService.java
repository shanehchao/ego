package com.ego.service;

import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午10:59
 */
public interface TbContentDubboService {

    // 分页查询内容分类具体内容
    List<TbContent> selectByCategoryId(long categoryId, int page, int rows);

    // 根据分类id查询所有该分类的记录数
    long selectCountTotal(long categoryId);
}
