package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.manage.service.TbContentService;
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

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows) {
        return tbContentService.selectByCategoryId(categoryId, page, rows);
    }

}
