package com.ego.search.dao;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/2 上午10:58
 */
public interface SolrDao {

    // 根据id删除solr中数据
    int deleteById(String id);

    // 删除solr中所有数据
    int deleteAll();

    // 新增solr数据
    int insert(SolrInputDocument doc);

    // 查询solr数据
    QueryResponse query(SolrParams params) throws SolrServerException;
}
