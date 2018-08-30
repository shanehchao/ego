package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午6:15
 */
@Controller
public class TbContentCategoryController {

    @Resource
    private TbContentCategoryService tbContentCategoryService;

    // 根据内容分类pid查询所有子分类
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITree> selectByPid(@RequestParam(value = "id", defaultValue = "0") long pid) {
        return tbContentCategoryService.selectByPid(pid);
    }

    // 新增内容分类
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult insertTbContentCategory(TbContentCategory tbContentCategory) {
        return tbContentCategoryService.insertTbContentCategory(tbContentCategory);
    }

    // 修改内容分类
    @RequestMapping("/content/category/update")
    @ResponseBody
    public EgoResult updateTbContentCategory(TbContentCategory tbContentCategory) {
        return tbContentCategoryService.updateTbContentCategory(tbContentCategory);
    }

    // 删除内容分类
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public EgoResult deleteTbContentCategory(TbContentCategory tbContentCategory) {
        return tbContentCategoryService.deleteTbContentCategory(tbContentCategory);
    }

}
