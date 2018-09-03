package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.manage.dao.JedisDao;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:09
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.bigpic.key}")
    private String bigpic;

    // 分页查询内容分类具体内容
    @Override
    public EasyUiDataGrid selectByCategoryId(long categoryId, int page, int rows) {
        EasyUiDataGrid easyUiDataGrid = new EasyUiDataGrid();
        easyUiDataGrid.setTotal(tbContentDubboService.selectCountTotal(categoryId));
        easyUiDataGrid.setRows(tbContentDubboService.selectByCategoryId(categoryId, page, rows));
        return easyUiDataGrid;
    }

    // 新增内容管理
    @Override
    public EgoResult insertTbContent(TbContent tbContent) {
        EgoResult er = new EgoResult();
        int index = tbContentDubboService.insertTbContent(tbContent);
        if (index>0) {
            // Redis缓存中有数据，则添加
            if (jedisDao.exists(bigpic)) {
                String json = jedisDao.get(bigpic);
                if (!StringUtils.isEmpty(json)) {
                    List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
                    tbContents.add(tbContent);
                    String result = jedisDao.set(bigpic, JsonUtils.objectToJson(tbContents));
                    if (!"OK".equalsIgnoreCase(result)) {
                        // 清空缓存
                        jedisDao.del(bigpic);
                    }
                }
            }
            er.setStatus(200);
        } else {
            er.setStatus(400);
        }
        return er;
    }

    // 修改内容管理
    @Override
    public EgoResult updateTbContent(TbContent tbContent) {
        EgoResult er = new EgoResult();
        int index = tbContentDubboService.updateTbContent(tbContent);
        if (index>0) {
            // Redis缓存中有数据，则修改
            if (jedisDao.exists(bigpic)) {
                String json = jedisDao.get(bigpic);
                if (!StringUtils.isEmpty(json)) {
                    List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
                    tbContents.add(tbContent);
                    String result = jedisDao.set(bigpic, JsonUtils.objectToJson(tbContents));
                    if ("OK".equalsIgnoreCase(result)) {
                        er.setStatus(200);
                    } else {
                        // 清空缓存
                        jedisDao.del(bigpic);
                    }
                } else {
                    // 缓存为空，不操作
                    er.setStatus(200);
                }
                // 当前key缓存不存在，不操作
            } else {
                er.setStatus(200);
            }
        } else {
            er.setStatus(400);
        }
        return er;
    }

    // 删除内容管理
    @Override
    public EgoResult deleteTbContent(String ids) {
        String[] idsArr = ids.split(",");
        int index = 0;
        for (String id : idsArr) {
            index += tbContentDubboService.deleteTbContent(Long.parseLong(id));
        }
        EgoResult er = new EgoResult();
        if (index==idsArr.length) {
            er.setStatus(200);
        }
        return er;
    }
}
