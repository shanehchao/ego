package com.ego.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午5:49
 */
public interface TbContentCategoryDubboServie {

    // 根据内容分类pid查询所有子分类
    List<TbContentCategory> selectByPid(long pid);

}
