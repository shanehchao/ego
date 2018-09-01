package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:08
 */
public interface TbContentService {

    // 分页查询内容分类具体内容
    EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows);

    // 新增内容管理
    EgoResult insertTbContent(TbContent tbContent);

    // 修改内容管理
    EgoResult updateTbContent(TbContent tbContent);

    // 删除内容管理
    EgoResult deleteTbContent(String ids);
}
