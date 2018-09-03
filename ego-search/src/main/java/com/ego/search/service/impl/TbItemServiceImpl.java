package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.dao.SolrDao;
import com.ego.search.pojo.SolrItem;
import com.ego.search.service.TbItemService;
import com.ego.service.TbItemCatDubboService;
import com.ego.service.TbItemDescDubboService;
import com.ego.service.TbItemDubboService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/2 上午11:48
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Resource
    private SolrDao solrDao;

    @Value("${solr.rows}")
    private int rows;

    //向solr中添加数据
    @Override
    public void insertSolr() {
        List<TbItem> tbItems = tbItemDubboService.selectAll();
        for (TbItem tbItem: tbItems) {
            // 创建solr对象
            SolrInputDocument doc = new SolrInputDocument();
            Long id = tbItem.getId();
            doc.setField("id", id);
            doc.setField("item_title", tbItem.getTitle());
            doc.setField("item_sell_point", tbItem.getSellPoint());
            doc.setField("item_price", tbItem.getPrice());
            doc.setField("item_image", tbItem.getImage());

            TbItemCat tbItemCat = tbItemCatDubboService.selectTbItemCatById(tbItem.getCid());
            if (tbItemCat != null) {
                doc.setField("item_category_name", tbItemCat.getName());
            }
            TbItemDesc tbItemDesc = tbItemDescDubboService.selectByTbItemId(id);
            if (tbItemDesc != null) {
                doc.setField("item_desc", tbItemDesc.getItemDesc());
            }

            solrDao.insert(doc);
        }
    }

    // 查询solr数据
    @Override
    public Map<String, Object> selectAllSolrData(String q, int page) throws SolrServerException {
        Map<String, Object> result = new HashMap<>();
        List<SolrItem> solrItemList = new ArrayList<>();

        // 创建查询对象
        SolrQuery params = new SolrQuery();
        // 添加查询条件
        params.setQuery("item_keywords:"+q);
        // 分页
        params.setStart(rows*(page-1));
        params.setRows(rows);
        // 开启高亮
        params.setHighlight(true);
        // 设置高亮显示字段
        params.addHighlightField("item_title item_sell_point");
        // 设置高亮前缀
        params.setHighlightSimplePre("<span style='font-weight:bold;color:red'>");
        // 设置高亮后缀
        params.setHighlightSimplePost("</span>");

        QueryResponse query = solrDao.query(params);
        SolrDocumentList response = query.getResults();
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
        // 如果有高亮则显示高亮，没有高亮则显示原内容
        for (SolrDocument doc : response) {
            SolrItem solrItem = new SolrItem();
            Object id = doc.getFieldValue("id");
            Map<String, List<String>> map = highlighting.get(id);
            if (map!=null) {
                List<String> itemTitleList = map.get("item_title");
                if (CollectionUtils.isEmpty(itemTitleList)) {
                    solrItem.setTitle(doc.getFieldValue("item_title")+"");
                } else {
                    solrItem.setTitle(itemTitleList.get(0));
                }
                List<String> itemSellPointList = map.get("item_sell_point");
                if (CollectionUtils.isEmpty(itemSellPointList)) {
                    solrItem.setSellPoint(doc.getFieldValue("item_sell_point")+"");
                } else {
                    solrItem.setSellPoint(itemSellPointList.get(0));
                }
            }
            solrItem.setId(Long.parseLong(id+""));
            solrItem.setPrice(Long.parseLong(doc.getFieldValue("item_price")+""));
            String[] itemImages = doc.getFieldValue("item_image").toString().split(",");
            if (ObjectUtils.isEmpty(itemImages)) {
                itemImages = new String[1];
            }
            solrItem.setImages(itemImages);
            solrItemList.add(solrItem);
        }
        result.put("itemList", solrItemList);
        // 获取总记录数
        long count = response.getNumFound();
        result.put("totalPages", count%rows==0 ? count/rows : count/rows+1);
        return result;
    }

    // 根据id删除solr中数据
    @Override
    public EgoResult deleteById(String id) {
        EgoResult er = new EgoResult();
        int index = solrDao.deleteById(id);
        if (index == 0) {
            er.setStatus(200);
        }
        return er;
    }

    // 删除solr中所有数据
    @Override
    public EgoResult deleteAll() {
        EgoResult er = new EgoResult();
        int index = solrDao.deleteAll();
        if (index == 0) {
            er.setStatus(200);
        }
        return er;
    }

    // 新增solr数据
    @Override
    public EgoResult insert(Map<String, String> param) {
        EgoResult er = new EgoResult();
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", param.get("id"));
        doc.setField("item_title", param.get("item_title"));
        doc.setField("item_sell_point", param.get("item_sell_point"));
        doc.setField("item_price", param.get("item_price"));
        doc.setField("item_image", param.get("item_image"));
        doc.setField("item_category_name", param.get("item_category_name"));
        doc.setField("item_desc", param.get("item_desc"));
        int index = solrDao.insert(doc);
        if (index == 0) {
            er.setStatus(200);
        }
        return er;
    }
}
