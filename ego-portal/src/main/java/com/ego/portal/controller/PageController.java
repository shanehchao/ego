package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class PageController {

    @Resource
    private TbContentService tbContentService;

    //显示主页
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("ad1", tbContentService.showBigPic());
        return "/WEB-INF/jsp/index.jsp";
    }
}
