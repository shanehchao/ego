package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class TbItemParamController {
    @Resource
    private TbItemParamService tbItemParamService;

    //分页查询
    @RequestMapping("/item/param/list")
    @ResponseBody
    public EasyUiDataGrid selectTbItemParamByPage(int page, int rows) {
        return tbItemParamService.selectTbItemParamByPage(page, rows);
    }

    //根据商品类目id查询商品规格参数
    @RequestMapping("/item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult selectTbItemParamByCatId(@PathVariable long catId) {
        EgoResult egoResult = new EgoResult();
        TbItemParam tbItemParam = tbItemParamService.selectTbItemParamByCatId(catId);
        if (tbItemParam != null) {
            egoResult.setStatus(200);
            egoResult.setData(tbItemParam);
        } else {
            egoResult.setStatus(400);
        }
        return egoResult;
    }

    //新增
    @RequestMapping("/item/param/save/{catId}")
    @ResponseBody
    public EgoResult insertTbItemParam(@PathVariable long catId, String paramData) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(catId);
        tbItemParam.setParamData(paramData);
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int result = tbItemParamService.insertTbItemParam(tbItemParam);
        EgoResult egoResult = new EgoResult();
        if (result == 1) {
            egoResult.setStatus(200);
        } else {
            egoResult.setStatus(400);
        }
        return egoResult;
    }

    //删除
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public EgoResult deleteTbItemParam(String ids) {
        int result = tbItemParamService.deleteTbItemParam(ids);
        EgoResult egoResult = new EgoResult();
        if (result == 1) {
            egoResult.setStatus(200);
        } else {
            egoResult.setStatus(400);
        }
        return egoResult;
    }
}
