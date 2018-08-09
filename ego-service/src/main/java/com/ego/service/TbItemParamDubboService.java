package com.ego.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
    //分页查询
    EasyUiDataGrid selectTbItemParamByPage(int page, int rows);

    //根据商品类目id查询商品规则参数
    TbItemParam selectTbItemParamByCatId(long catId);

    //新增
    int insertTbItemParam(TbItemParam tbItemParam);

    //删除
    int deleteTbItemParam(long id);
}
