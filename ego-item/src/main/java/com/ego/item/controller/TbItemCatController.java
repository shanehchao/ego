package com.ego.item.controller;

import com.ego.item.service.TbItemCatService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午4:12
 */
@Controller
public class TbItemCatController {

    @Resource
    private TbItemCatService tbItemCatService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showCat(String callback) {
        MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatService.showCat());
        mjv.setJsonpFunction(callback);
        return mjv;
    }

}
