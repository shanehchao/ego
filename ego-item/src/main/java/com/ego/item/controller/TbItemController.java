package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午4:10
 */
@Controller
public class TbItemController {

    @Resource
    private TbItemService tbItemService;

    @RequestMapping("/item/{id}.html")
    public String showItem(@PathVariable long id, Model model) {
        model.addAttribute("item", tbItemService.selectByTbItemId(id));
        return "/WEB-INF/jsp/item.jsp";
    }

}
