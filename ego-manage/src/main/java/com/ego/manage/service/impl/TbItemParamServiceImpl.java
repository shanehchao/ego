package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemCatDubboService;
import com.ego.service.TbItemParamDubboService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {
    @Reference
    private TbItemParamDubboService tbItemParamDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    //分页查询
    @Override
    public EasyUiDataGrid selectTbItemParamByPage(int page, int rows) {
        EasyUiDataGrid easyUiDataGrid = tbItemParamDubboService.selectTbItemParamByPage(page, rows);
        List<TbItemParam> list = (List<TbItemParam>) easyUiDataGrid.getRows();
        List<TbItemParamChild> childList = new ArrayList<>();
        for (TbItemParam tbItemParam : list) {
            TbItemParamChild tbItemParamChild = new TbItemParamChild();
            tbItemParamChild.setId(tbItemParam.getId());
            tbItemParamChild.setItemCatId(tbItemParam.getItemCatId());
            tbItemParamChild.setParamData(tbItemParam.getParamData());
            tbItemParamChild.setCreated(tbItemParam.getCreated());
            tbItemParamChild.setUpdated(tbItemParam.getUpdated());
            tbItemParamChild.setItemCatName(tbItemCatDubboService.selectTbItemCatById(tbItemParamChild.getItemCatId()).getName());
            childList.add(tbItemParamChild);
        }
        easyUiDataGrid.setRows(childList);
        return easyUiDataGrid;
    }

    //根据商品类目id查询商品规格参数
    @Override
    public TbItemParam selectTbItemParamByCatId(long catId) {
        return tbItemParamDubboService.selectTbItemParamByCatId(catId);
    }

    //新增
    @Override
    public int insertTbItemParam(TbItemParam tbItemParam) {
        return tbItemParamDubboService.insertTbItemParam(tbItemParam);
    }

    //删除
    @Override
    public int deleteTbItemParam(String ids) {
        String[] idArr = ids.split(",");
        int result = 0;
        for (String id : idArr) {
            result += tbItemParamDubboService.deleteTbItemParam(Long.parseLong(id));
        }
        if (result == idArr.length) {
            return 1;
        }
        return 0;
    }
}
