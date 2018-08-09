package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.ego.service.TbItemDubboService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUiDataGrid selectTbItemByPage(int page, int rows) {
        //启动pagehelper分页插件，并设置分页条件
        PageHelper.startPage(page, rows);

        //创建查询条件
        TbItemExample example = new TbItemExample();
        //查询所有记录
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);

        //将查询到的记录封装到pageinfo对象中
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);

        //返回需要的数据
        EasyUiDataGrid easyUiDataGrid = new EasyUiDataGrid();
        easyUiDataGrid.setTotal(pageInfo.getTotal());
        easyUiDataGrid.setRows(pageInfo.getList());

        return easyUiDataGrid;
    }

    /**
     * 根据商品id修改商品状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public int updateTbItemStatusById(long id, byte status) {
        TbItem tbItem = new TbItem();
        tbItem.setId(id);
        tbItem.setStatus(status);
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int insertTbItem(TbItem tbItem) {
        return tbItemMapper.insert(tbItem);
    }
}
