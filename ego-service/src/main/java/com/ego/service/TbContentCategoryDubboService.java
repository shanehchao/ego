package com.ego.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午5:49
 */
public interface TbContentCategoryDubboService {

    // 根据内容分类pid查询所有子分类
    List<TbContentCategory> selectByPid(long pid);

    // 新增内容分类
    int insertTbContentCategory(TbContentCategory tbContentCategory);

    // 查询（同一父类目下是否具有同名的子类目）
    List<TbContentCategory> selectByTbContentCategory(TbContentCategory tbContentCategory);

    // 修改内容分类
    int updateTbContentCategory(TbContentCategory tbContentCategory);
}
