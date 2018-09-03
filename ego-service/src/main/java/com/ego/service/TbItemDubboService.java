package com.ego.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;

import java.util.List;

public interface TbItemDubboService {
    // 分页查询
    EasyUiDataGrid selectTbItemByPage(int page, int rows);

    // 根据商品id修改商品状态
    int updateTbItemStatusById(long id, byte status);

    // 新增商品
    int insertTbItem(TbItem tbItem);

    // 查询所有商品
    List<TbItem> selectAll();

    // 根据id查询商品信息
    TbItem selectTbItemById(long id);
}
