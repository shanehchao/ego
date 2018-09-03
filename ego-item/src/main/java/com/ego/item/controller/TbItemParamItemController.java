package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午5:06
 */
@Controller
public class TbItemParamItemController {

    @Resource
    private TbItemParamItemService tbItemParamItemService;

    @RequestMapping(value = "/item/param/{id}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showParam(@PathVariable long id) {
        String result = "此商品暂时规格参数";
        String temp = tbItemParamItemService.selectByTbItemId(id);
        if (StringUtils.isEmpty(temp)) {
            return result;
        }
        return temp;
    }
}
