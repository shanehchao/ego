package com.ego.search.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/2 下午12:01
 */
@Controller
public class TbItemServiceController {

    @Resource
    private TbItemService tbItemService;

    @RequestMapping(value="/init/solr", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String initSolr() {
        long startTime = System.currentTimeMillis();
        tbItemService.insertSolr();
        long endTime = System.currentTimeMillis();
        return "初始化成功，用时：" + (endTime - startTime) / 1000 / 60 + "min";
    }

    @RequestMapping({"/search.html", "/search"})
    public String showPage(String q, @RequestParam(defaultValue = "1") int page, Model model) {
        try {
            q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
            Map<String, Object> map = tbItemService.selectAllSolrData(q, page);
            model.addAttribute("itemList", map.get("itemList"));
            model.addAttribute("totalPages", map.get("totalPages"));
            model.addAttribute("query", q);
            model.addAttribute("page", page);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/search.jsp";
    }

    // 根据id删除solr中数据
    @RequestMapping("/solr/delete")
    @ResponseBody
    public EgoResult deleteById(@RequestParam Map<String, String> param) {
        return tbItemService.deleteById(param.get("id"));
    }

    // 删除solr中所有数据
    @RequestMapping("/solr/deleteall")
    @ResponseBody
    public EgoResult deleteAll() {
        return tbItemService.deleteAll();
    }

    // 新增solr数据
    @RequestMapping("/solr/insert")
    @ResponseBody
    public EgoResult insert(@RequestParam Map<String, String> param) {
        return tbItemService.insert(param);
    }

}
