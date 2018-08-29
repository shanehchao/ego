package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类目
 */
@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatService;

    //根据父类目id查询所有子类目
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITree> selectTbItemCatById(@RequestParam(value = "id", defaultValue = "0") long pid) {
        return tbItemCatService.selectTbItemCatByPid(pid);
    }

}
