package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbItem;
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
            doc.setField("item_category_name", tbItemCatDubboService.selectTbItemCatById(tbItem.getCid()).getName());
            doc.setField("item_desc", tbItemDescDubboService.selectByTbItemId(id));
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
        long count = response.getNumFound();
        result.put("totalPages", count%rows==0 ? count/rows : count/rows+1);
        return result;
    }
}
