package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    //显示欢迎页面
    @RequestMapping("/")
    public String welcome() {
        return "/WEB-INF/jsp/index.jsp";
    }

    //显示其他页面
    @RequestMapping("{page}")
    public String welcome(@PathVariable String page) {
        return "/WEB-INF/jsp/" + page + ".jsp";
    }

}
