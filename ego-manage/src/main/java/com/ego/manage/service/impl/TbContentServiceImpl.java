package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.manage.service.TbContentService;
import com.ego.service.TbContentDubboService;
import org.springframework.stereotype.Service;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:09
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;

    // 分页查询内容分类具体内容
    @Override
    public EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows) {
        EasyUiDataGrid easyUiDataGrid = new EasyUiDataGrid();
        easyUiDataGrid.setTotal(tbContentDubboService.selectCountTotal(categoryId));
        easyUiDataGrid.setRows(tbContentDubboService.selectByCategoryId(categoryId, page, rows));
        return easyUiDataGrid;
    }
}
