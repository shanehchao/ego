package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.ego.service.TbItemParamDubboService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Resource
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public EasyUiDataGrid selectTbItemParamByPage(int page, int rows) {
        //启动pagehelper插件
        PageHelper.startPage(page, rows);
        //创建查询条件
        TbItemParamExample example = new TbItemParamExample();
        //查询所有数据
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        //将查询数据封装到pageinfo中
        PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(tbItemParams);
        //返回需要的数据
        EasyUiDataGrid easyUiDataGrid = new EasyUiDataGrid();
        easyUiDataGrid.setTotal(pageInfo.getTotal());
        easyUiDataGrid.setRows(pageInfo.getList());

        return easyUiDataGrid;
    }

    //根据商品类目id查询商品规则参数
    @Override
    public TbItemParam selectTbItemParamByCatId(long catId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (tbItemParams != null && tbItemParams.size() > 0) {
            return tbItemParams.get(0);
        }
        return null;
    }

    //新增
    @Override
    public int insertTbItemParam(TbItemParam tbItemParam) {
        return tbItemParamMapper.insertSelective(tbItemParam);
    }

    //删除
    @Override
    public int deleteTbItemParam(long id) {
        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
