package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午4:46
 */
@Controller
public class TbItemDescController {

    @Resource
    private TbItemDescService tbItemDescService;

    @RequestMapping(value = "/item/desc/{id}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showDesc(@PathVariable long id) {
        String itemDesc = "此商品暂时没有描述";
        TbItemDesc tbItemDesc = tbItemDescService.selectByTbItemId(id);
        if (tbItemDesc!=null) {
            itemDesc = tbItemDesc.getItemDesc();
        }
        return itemDesc;
    }
}
