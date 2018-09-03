package com.ego.search.service;

import com.ego.commons.pojo.EgoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/2 上午11:48
 */
public interface TbItemService {
    // 向solr中添加数据
    void insertSolr();

    // 查询solr数据
    Map<String, Object> selectAllSolrData(String q, int page) throws SolrServerException;

    // 根据id删除solr中数据
    EgoResult deleteById(String id);

    // 删除solr中所有数据
    EgoResult deleteAll();

    // 新增solr数据
    EgoResult insert(Map<String, String> params);
}
