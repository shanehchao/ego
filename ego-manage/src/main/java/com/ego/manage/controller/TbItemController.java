package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品
 */
@Controller
public class TbItemController {

    @Resource
    private TbItemService tbItemServiceImpl;

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUiDataGrid selectTbItemByPage(int page, int rows) {
        return tbItemServiceImpl.selectTbItemByPage(page, rows);
    }

    //根据商品id修改商品状态
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult updateStatusById(String ids) {
        EgoResult egoResult = new EgoResult();
        try {
            int result = tbItemServiceImpl.updateTbItemStatusById(ids, (byte) 3);
            if (result == 1) {
                egoResult.setStatus(200);
            } else {
                egoResult.setStatus(400);
            }
        } catch (Exception e) {
            egoResult.setStatus(500);
            e.printStackTrace();
        }
        return egoResult;
    }

    //下架
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult updateStatusByIdInstock(String ids) {
        EgoResult egoResult = new EgoResult();
        try {
            int result = tbItemServiceImpl.updateTbItemStatusById(ids, (byte) 2);
            if (result == 1) {
                egoResult.setStatus(200);
            } else {
                egoResult.setStatus(400);
            }
        } catch (Exception e) {
            egoResult.setStatus(500);
            e.printStackTrace();
        }
        return egoResult;
    }

    //上架
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult updateStatusByIdReshelf(String ids) {
        EgoResult egoResult = new EgoResult();
        try {
            int result = tbItemServiceImpl.updateTbItemStatusById(ids, (byte) 1);
            if (result == 1) {
                egoResult.setStatus(200);
            } else {
                egoResult.setStatus(400);
            }
        } catch (Exception e) {
            egoResult.setStatus(500);
            e.printStackTrace();
        }
        return egoResult;
    }

    //上传文件
    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile uploadFile) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = tbItemServiceImpl.upload(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //新增商品
    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insertItem(TbItem tbItem, String desc, String itemParams) {
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.insertTbItem(tbItem, desc, itemParams);
        if (index == 1) {
            egoResult.setStatus(200);
        } else {
            egoResult.setStatus(400);
        }
        return egoResult;
    }
}
