package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:08
 */
public interface TbContentService {

    // 分页查询内容分类具体内容
    EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows);
}
