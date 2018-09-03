package com.ego.search.dao.impl;

import com.ego.search.dao.SolrDao;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/2 上午10:58
 */
@Repository
public class SolrDaoImpl implements SolrDao {

    @Resource
    private CloudSolrServer cloudSolrServer;

    // 根据id删除solr中数据
    @Override
    public int deleteById(String id) {
        int status = -1;
        try {
            UpdateResponse updateResponse = cloudSolrServer.deleteById(id);
            status = updateResponse.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    // 删除solr中所有数据
    @Override
    public int deleteAll() {
        int status = -1;
        try {
            UpdateResponse updateResponse = cloudSolrServer.deleteByQuery("*:*");
            status = updateResponse.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    // 新增solr数据
    @Override
    public int insert(SolrInputDocument doc) {
        int status = -1;
        try {
            UpdateResponse response = cloudSolrServer.add(doc);
            status = response.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    // 查询solr数据
    public QueryResponse query(SolrParams params) throws SolrServerException {
        return cloudSolrServer.query(params);
    }
}
