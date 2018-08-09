package com.ego.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;

public interface TbItemDubboService {
    //分页查询
    EasyUiDataGrid selectTbItemByPage(int page, int rows);

    //根据商品id修改商品状态
    int updateTbItemStatusById(long id, byte status);

    //新增商品
    int insertTbItem(TbItem tbItem);
}
