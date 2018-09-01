package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:18
 */
@Controller
public class TbContentContoller {

    @Resource
    private TbContentService tbContentService;

    // 分页查询内容分类具体内容
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows) {
        return tbContentService.selectByCategoryId(categoryId, page, rows);
    }

    // 新增内容管理
    @RequestMapping("/content/save")
    @ResponseBody
    public EgoResult insertTbContent(TbContent tbContent) {
        return tbContentService.insertTbContent(tbContent);
    }

    // 修改内容管理
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public EgoResult updateTbContent(TbContent tbContent) {
        return tbContentService.updateTbContent(tbContent);
    }

    // 删除内容管理
    @RequestMapping("/content/delete")
    @ResponseBody
    public EgoResult deleteTbContent(String ids) {
        return tbContentService.deleteTbContent(ids);
    }
}
