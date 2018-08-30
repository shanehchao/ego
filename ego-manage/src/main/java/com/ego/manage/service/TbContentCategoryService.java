package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午6:01
 */
public interface TbContentCategoryService {

    // 根据内容分类pid查询所有子分类
    List<EasyUITree> selectByPid(long pid);

    // 新增内容分类
    EgoResult insertTbContentCategory(TbContentCategory tbContentCategory);

    // 修改内容分类
    EgoResult updateTbContentCategory(TbContentCategory tbContentCategory);

    // 删除内容分类
    EgoResult deleteTbContentCategory(TbContentCategory tbContentCategory);
}
